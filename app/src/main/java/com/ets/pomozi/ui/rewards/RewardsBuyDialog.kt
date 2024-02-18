package com.ets.pomozi.ui.rewards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ets.pomozi.R
import com.ets.pomozi.databinding.DialogRewardsBuyBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RewardsBuyDialog : BottomSheetDialogFragment() {
    private val args: RewardsBuyDialogArgs by navArgs()

    private var _binding: DialogRewardsBuyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogRewardsBuyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogRewardsBuyTextTitle.text = args.reward.title
        binding.dialogRewardsBuyTextDescription.text = args.reward.description
        binding.dialogRewardsBuyButtonTextPoints.text = args.reward.price.toString()

        Glide.with(requireContext())
            .load(args.reward.photo)
            .into(binding.dialogRewardsBuyPhoto)
    }
}