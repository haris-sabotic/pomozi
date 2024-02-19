package com.ets.pomozi.ui.rewards

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ets.pomozi.R
import com.ets.pomozi.databinding.FragmentRewardsBinding
import com.ets.pomozi.models.RewardModel
import com.ets.pomozi.ui.UserViewModel
import com.ets.pomozi.util.GlobalData
import com.ets.pomozi.util.setPhoto
import okhttp3.internal.notifyAll

class RewardsFragment : Fragment() {
    val userViewModel: UserViewModel by activityViewModels()
    val rewardsViewModel: RewardsViewModel by activityViewModels()

    private var _binding: FragmentRewardsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var rewards = arrayListOf<RewardModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRewardsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rewardsViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        rewardsViewModel.loadRewards("")
        binding.rewardsSwipeRefresh.isRefreshing = true

        binding.rewardsSwipeRefresh.setOnRefreshListener {
            val text: String = binding.rewardsEdittextSearch.text.toString()
            rewardsViewModel.loadRewards(text.trim())
        }

        // setup user photo and points
        userViewModel.userData.value?.let {
            setPhoto(requireContext(), binding.rewardsHeaderPhoto, it.photo, R.drawable.default_user)
            binding.rewardsTextPoints.text = it.points.toString()
        }
        userViewModel.userData.observe(viewLifecycleOwner) {
            setPhoto(requireContext(), binding.rewardsHeaderPhoto, it.photo, R.drawable.default_user)
            binding.rewardsTextPoints.text = it.points.toString()
        }

        // Add gradient to title
        val paint: TextPaint = binding.rewardsTextTitle.paint
        val width: Float = paint.measureText(binding.rewardsTextTitle.text.toString())
        val colors = intArrayOf(Color.parseColor("#FE724D"), Color.parseColor("#FFC529"));
        val textShader: Shader = LinearGradient(0F, 0F, width, binding.rewardsTextTitle.textSize, colors, null, Shader.TileMode.CLAMP)
        binding.rewardsTextTitle.paint.setShader(textShader)

        binding.rewardsRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rewardsRecycler.adapter = RewardsRecyclerViewAdapter(requireContext(), rewards) { reward ->
            val action = RewardsFragmentDirections.actionRewardsToDialogRewardsBuy(reward)
            findNavController().navigate(action)
        }

        binding.rewardsEdittextSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text: String = binding.rewardsEdittextSearch.text.toString()
                rewardsViewModel.loadRewards(text.trim())
                binding.rewardsSwipeRefresh.isRefreshing = true
                return@setOnEditorActionListener true
            }

            false
        }

        rewardsViewModel.rewards.observe(viewLifecycleOwner) {
            binding.rewardsSwipeRefresh.isRefreshing = false
            rewards.clear()
            for (reward in it) {
                rewards.add(reward)
            }

            binding.rewardsRecycler.adapter?.notifyDataSetChanged()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
