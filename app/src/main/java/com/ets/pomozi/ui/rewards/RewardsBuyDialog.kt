package com.ets.pomozi.ui.rewards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ets.pomozi.R
import com.ets.pomozi.databinding.DialogRewardsBuyBinding
import com.ets.pomozi.util.GlobalData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jp.wasabeef.glide.transformations.BlurTransformation

class RewardsBuyDialog : BottomSheetDialogFragment() {
    private val rewardsViewModel: RewardsViewModel by activityViewModels()

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
            .load(GlobalData.PHOTO_PREFIX + args.reward.photo)
            .into(binding.dialogRewardsBuyPhoto)

        binding.dialogRewardsBuyButton.setOnClickListener {
            rewardsViewModel.buyReward(args.reward.id)
        }

        rewardsViewModel.bought.observe(viewLifecycleOwner) { bought ->
            if (bought) {
                Toast.makeText(requireContext(), "Uspješno ste kupili nagradu", Toast.LENGTH_SHORT).show()
            }
        }

        rewardsViewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }
}