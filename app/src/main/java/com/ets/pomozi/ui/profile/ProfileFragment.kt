package com.ets.pomozi.ui.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ets.pomozi.R
import com.ets.pomozi.api.requests.EditUserRequest
import com.ets.pomozi.databinding.FragmentProfileBinding
import com.ets.pomozi.models.DonationModel
import com.ets.pomozi.models.UserRewardModel
import com.ets.pomozi.ui.UserViewModel
import com.ets.pomozi.util.setPhoto
import com.ets.pomozi.util.showInputDialog


class ProfileFragment : Fragment() {
    val userViewModel: UserViewModel by activityViewModels()

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var activityList = arrayListOf<DonationModel>()
    private var rewardsList = arrayListOf<UserRewardModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileRecyclerviewActivity.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.profileRecyclerviewActivity.adapter = ActivityRecyclerViewAdapter(requireContext(), activityList)

        binding.profileRecyclerviewRewards.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.profileRecyclerviewRewards.adapter = UserRewardsRecyclerViewAdapter(requireContext(), rewardsList)

        userViewModel.loadUserData()
        userViewModel.loadDonations()
        userViewModel.loadRewards()
        binding.profileSwipeRefresh.isRefreshing = true
        binding.profileSwipeRefresh.setOnRefreshListener {
            userViewModel.loadUserData()
            userViewModel.loadDonations()
            userViewModel.loadRewards()
        }

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
            binding.profileSwipeRefresh.isRefreshing = false
            setPhoto(requireContext(), binding.profilePhoto, userData.photo, R.drawable.default_user)

            binding.profileTextName.text = userData.name
            binding.profileInfoMoneyTextAmount.text = "${userData.donatedAmount}€"
            binding.profileInfoTextAboutMeContent.text = userData.about

            val totalAchievements = 16
            val achievementsAcquired = userData.achievements.numberAcquired()
            binding.profileInfoTextAchievementsCount.text = "${achievementsAcquired}/${totalAchievements}"

            binding.profileInfoAchievementOneYearImage.imageTintList = ContextCompat.getColorStateList(
                requireContext(),
                if (userData.achievements.one_year) R.color.achievement_active else R.color.achievement_inactive
            )

            binding.profileInfoAchievementTenDonationsImage.imageTintList = ContextCompat.getColorStateList(
                requireContext(),
                if (userData.achievements.ten_donations) R.color.achievement_active else R.color.achievement_inactive
            )

            binding.profileInfoAchievementHundredDonatedImage.imageTintList = ContextCompat.getColorStateList(
                requireContext(),
                if (userData.achievements.hundred_donated) R.color.achievement_active else R.color.achievement_inactive
            )

            binding.profileInfoAchievementAccountCreatedImage.imageTintList = ContextCompat.getColorStateList(
                requireContext(),
                if (userData.achievements.account_created) R.color.achievement_active else R.color.achievement_inactive
            )
        }

        userViewModel.donations.observe(viewLifecycleOwner) { donations ->
            binding.profileSwipeRefresh.isRefreshing = false
            activityList.clear()
            activityList.addAll(donations.reversed())
            binding.profileRecyclerviewActivity.adapter?.notifyDataSetChanged()
        }

        userViewModel.rewards.observe(viewLifecycleOwner) { rewards ->
            binding.profileSwipeRefresh.isRefreshing = false
            rewardsList.clear()
            rewardsList.addAll(rewards.reversed())
            binding.profileRecyclerviewRewards.adapter?.notifyDataSetChanged()
        }

        userViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        binding.profileInfoTextAboutMeContent.setOnClickListener {
            showInputDialog(requireContext(), "About", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE, 3) { about ->
                userViewModel.editUser(EditUserRequest(null, null, null, null, about, null))
                binding.profileSwipeRefresh.isRefreshing = true
            }
        }

        binding.profileTextEditProfile.setOnClickListener {
            userViewModel.userData.value?.let { userData ->
                val action = ProfileFragmentDirections.actionProfileToEditProfile(userData)
                findNavController().navigate(action)
            }
        }

        binding.profileTextActivity.setOnClickListener {
            binding.profileScrollviewInfo.visibility = View.GONE
            binding.profileRecyclerviewActivity.visibility = View.VISIBLE
            binding.profileRecyclerviewRewards.visibility = View.GONE
            binding.profileTextInfo.setTextColor(resources.getColor(R.color.gray_text_3))
            binding.profileTextActivity.setTextColor(resources.getColor(R.color.red_on_cool_blue_gray))
            binding.profileTextRewards.setTextColor(resources.getColor(R.color.gray_text_3))
            binding.profileUnderlineInfo.visibility = View.GONE
            binding.profileUnderlineActivity.visibility = View.VISIBLE
            binding.profileUnderlineRewards.visibility = View.GONE

            binding.profileSwipeRefresh.isRefreshing = true
            userViewModel.loadDonations()
        }

        binding.profileTextInfo.setOnClickListener {
            binding.profileScrollviewInfo.visibility = View.VISIBLE
            binding.profileRecyclerviewActivity.visibility = View.GONE
            binding.profileRecyclerviewRewards.visibility = View.GONE
            binding.profileTextInfo.setTextColor(resources.getColor(R.color.red_on_cool_blue_gray))
            binding.profileTextActivity.setTextColor(resources.getColor(R.color.gray_text_3))
            binding.profileTextRewards.setTextColor(resources.getColor(R.color.gray_text_3))
            binding.profileUnderlineInfo.visibility = View.VISIBLE
            binding.profileUnderlineActivity.visibility = View.GONE
            binding.profileUnderlineRewards.visibility = View.GONE
        }

        binding.profileTextRewards.setOnClickListener {
            binding.profileScrollviewInfo.visibility = View.GONE
            binding.profileRecyclerviewActivity.visibility = View.GONE
            binding.profileRecyclerviewRewards.visibility = View.VISIBLE
            binding.profileTextInfo.setTextColor(resources.getColor(R.color.gray_text_3))
            binding.profileTextActivity.setTextColor(resources.getColor(R.color.gray_text_3))
            binding.profileTextRewards.setTextColor(resources.getColor(R.color.red_on_cool_blue_gray))
            binding.profileUnderlineInfo.visibility = View.GONE
            binding.profileUnderlineActivity.visibility = View.GONE
            binding.profileUnderlineRewards.visibility = View.VISIBLE
        }

        binding.profileInfoArrowAchievemnts.setOnClickListener {
            userViewModel.userData.value?.let { userData ->
                val action = ProfileFragmentDirections.actionProfileToDialogAchievements(userData.achievements)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
