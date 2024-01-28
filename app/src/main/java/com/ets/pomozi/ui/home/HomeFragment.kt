package com.ets.pomozi.ui.home

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.text.TextPaint
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.ets.pomozi.R
import com.ets.pomozi.databinding.FragmentHomeBinding
import com.ets.pomozi.models.OrganizationModel
import com.ets.pomozi.ui.UserViewModel
import com.ets.pomozi.util.GlobalData
import com.ets.pomozi.util.setPhoto
import kotlin.math.abs


class HomeFragment : Fragment() {
    val userViewModel: UserViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var organizations = arrayListOf<OrganizationModel>()

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

        setOrganizations("")

        // setup user photo
        userViewModel.userData.value?.let { setPhoto(requireContext(), binding.registerHeaderPhoto, it.photo, R.drawable.default_user) }
        userViewModel.userData.observe(viewLifecycleOwner) { setPhoto(requireContext(), binding.registerHeaderPhoto, it.photo, R.drawable.default_user) }

        // Add gradient to title
        val paint: TextPaint = binding.registerTextDonate.paint
        val width: Float = paint.measureText(binding.registerTextDonate.text.toString())
        val colors = intArrayOf(Color.parseColor("#FE724D"), Color.parseColor("#FFC529"));
        val textShader: Shader = LinearGradient(0F, 0F, width, binding.registerTextDonate.textSize, colors, null, Shader.TileMode.CLAMP)
        binding.registerTextDonate.paint.setShader(textShader)

        // set up card slider
        binding.homeViewpager.adapter = ViewPagerAdapter(requireContext(), organizations)

        binding.homeViewpager.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
        }
        binding.homeViewpager.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.homeViewpager.addItemDecoration(itemDecoration)

        binding.homeEdittextSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text: String = binding.homeEdittextSearch.text.toString()
                setOrganizations(text.trim())
                return@setOnEditorActionListener true
            }

            false
        }
    }

    private fun setOrganizations(query: String) {
        organizations.clear()

        if (query.isEmpty()) {
            organizations.addAll(GlobalData.ORGANIZATIONS)
        } else {
            for (organization in GlobalData.ORGANIZATIONS) {
                if (organization.name.contains(query, true)) {
                    organizations.add(organization)
                }
            }
        }

        binding.homeViewpager.adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}