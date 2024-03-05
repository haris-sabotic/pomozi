package com.ets.pomozi.ui.home

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.TypefaceSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ets.pomozi.R
import com.ets.pomozi.databinding.FragmentHomeBinding
import com.ets.pomozi.models.ActionModel
import com.ets.pomozi.models.OrganizationModel
import com.ets.pomozi.util.addGradientToTextView
import com.ets.pomozi.util.setPhoto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs


class HomeFragment : Fragment() {
    val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var actions = mutableListOf<ActionModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.loadLast2Donations()
        homeViewModel.loadTopDonation()
        homeViewModel.loadActions()
        binding.homeSwipeRefresh.isRefreshing = true

        binding.homeSwipeRefresh.setOnRefreshListener {
            homeViewModel.loadLast2Donations()
            homeViewModel.loadTopDonation()
            homeViewModel.loadActions()
        }

        addGradientToTextView(binding.homeTextHeader, "#FE724D", "#FFC529")
        addGradientToTextView(binding.homeTextDonations, "#FE724D", "#FFC529")
        addGradientToTextView(binding.homeDonationPrimaryTitle, "#FE724D", "#FFC529")
        addGradientToTextView(binding.homeTextActions, "#FE724D", "#FFC529")

        binding.homeTextOrganizationList.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_organizations)
        }

        binding.homeTextLeaderboard.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_leaderboard)
        }

        homeViewModel.topDonation.observe(viewLifecycleOwner) {
            binding.homeSwipeRefresh.isRefreshing = false

            it?.let { topDonation ->
                // set photo
                setPhoto(requireContext(), binding.homeDonationPrimaryPhoto, topDonation.user.photo, R.drawable.default_user)

                // set text (with formatting)

                val part1 = topDonation.user.name
                val part2 = " je donirao/la "
                val part3 = "${topDonation.donatedAmount}€"
                val wholeText = part1 + part2 + part3
                val spanText = SpannableStringBuilder(wholeText)
                val blackTypeface = Typeface.create(ResourcesCompat.getFont(requireContext(), R.font.poppins_black), Typeface.NORMAL)

                spanText.setSpan(
                    TypefaceSpan(blackTypeface),
                    0,
                    part1.length,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )

                spanText.setSpan(
                    TypefaceSpan(blackTypeface),
                    wholeText.length - part3.length - 1,
                    wholeText.length-1,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )

                binding.homeDonationPrimaryContent.text = spanText

                // set gradient
                addGradientToTextView(binding.homeDonationPrimaryContent, "#FFC529", "#FE724D")
            }
        }

        homeViewModel.last2Donations.observe(viewLifecycleOwner) {
            binding.homeSwipeRefresh.isRefreshing = false

            it.first?.let { donation ->
                setPhoto(requireContext(), binding.homeDonationLeftPhoto, donation.user.photo, R.drawable.default_user)
                binding.homeDonationLeftTitle.text = donation.user.name
                binding.homeDonationLeftContent.text = "${donation.donatedAmount}€ donirano u ${donation.timestamp}"
            }

            it.second?.let { donation ->
                setPhoto(requireContext(), binding.homeDonationRightPhoto, donation.user.photo, R.drawable.default_user)
                binding.homeDonationRightTitle.text = donation.user.name
                binding.homeDonationRightContent.text = "${donation.donatedAmount}€ donirano u ${donation.timestamp}"
            }

            // MAKE THEM HAVE THE SAME HEIGHT (IF THEY DON'T ALREADY)
            viewLifecycleOwner.lifecycleScope.launch {
                delay(500)

                val leftHeight = binding.homeDonationLeftLayout.height
                val rightHeight = binding.homeDonationRightLayout.height

                if (leftHeight < rightHeight) {
                    val lp = binding.homeDonationLeftLayout.layoutParams
                    lp.height = rightHeight
                    binding.homeDonationLeftLayout.layoutParams = lp
                }

                if (rightHeight < leftHeight) {
                    val lp = binding.homeDonationRightLayout.layoutParams
                    lp.height = leftHeight
                    binding.homeDonationRightLayout.layoutParams = lp
                }
            }

        }

        // SET UP VIEWPAGER

        binding.homeViewpagerActions.adapter = ViewPagerAdapter(requireContext(), actions)

        binding.homeViewpagerActions.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
        }
        binding.homeViewpagerActions.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.homeViewpagerActions.addItemDecoration(itemDecoration)

        homeViewModel.actions.observe(viewLifecycleOwner) {
            binding.homeSwipeRefresh.isRefreshing = false

            actions.clear()
            for (action in it) {
                actions.add(action)
            }

            // infinite scroll
            val first = actions[0]
            val last = actions[actions.size-1]
            actions.add(0, last)
            actions.add(first)
            binding.homeViewpagerActions.setCurrentItem(1, false)
            val recyclerView = binding.homeViewpagerActions.getChildAt(0) as RecyclerView
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val itemCount = binding.homeViewpagerActions.adapter?.itemCount ?: 0
            recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val firstItemVisible
                            = layoutManager.findFirstVisibleItemPosition()
                    val lastItemVisible
                            = layoutManager.findLastVisibleItemPosition()
                    if (firstItemVisible == (itemCount - 1) && dx > 0) {
                        recyclerView.scrollToPosition(1)
                    } else if (lastItemVisible == 0 && dx < 0) {
                        recyclerView.scrollToPosition(itemCount - 2)
                    }
                }
            })

            binding.homeViewpagerActions.adapter?.notifyDataSetChanged()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}