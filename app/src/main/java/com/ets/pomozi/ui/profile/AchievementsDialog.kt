package com.ets.pomozi.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.ets.pomozi.R
import com.ets.pomozi.databinding.DialogAchievementsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AchievementsDialog : BottomSheetDialogFragment() {
    private val args: AchievementsDialogArgs by navArgs()

    private var _binding: DialogAchievementsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogAchievementsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalAchievements = 16
        val achievementsAcquired = args.achievements.numberAcquired()
        binding.dialogAchievementsTitleCount.text = "${achievementsAcquired}/${totalAchievements}"

        binding.dialogAchievementsOneYearImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.one_year) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsSixMonthsImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.six_months) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsHundredDonatedImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.hundred_donated) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsFiftyDonatedImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.fifty_donated) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsTenDonatedImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.ten_donated) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsTenDonationsImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.ten_donations) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsFirstDonationImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.first_donation) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsTenRewardsBoughtImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.ten_rewards_bought) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsFiveRewardsBoughtImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.five_rewards_bought) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsRewardBoughtImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.reward_bought) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsHundredPointsAcquiredImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.hundred_points_acquired) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsRankedImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.ranked) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsAccountCreatedImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.account_created) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsTopThreeImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.top_three) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsHundredDonationsImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.hundred_donations) R.color.achievement_active else R.color.achievement_inactive
        )

        binding.dialogAchievementsFiftyDonationsImage.imageTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (args.achievements.fifty_donations) R.color.achievement_active else R.color.achievement_inactive
        )
    }
}