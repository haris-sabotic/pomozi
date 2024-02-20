package com.ets.pomozi.ui.home

import android.graphics.Outline
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ets.pomozi.R
import com.ets.pomozi.databinding.FragmentLeaderboardBinding
import com.ets.pomozi.models.UserModel
import com.ets.pomozi.util.GlobalData
import com.ets.pomozi.util.addGradientToTextView
import com.ets.pomozi.util.setPhoto

class LeaderboardFragment : Fragment() {
    val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentLeaderboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var leaderboard = arrayListOf<UserModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.leaderboardArrowBack.setOnClickListener { findNavController().popBackStack() }

        homeViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        homeViewModel.loadLeaderboard()
        binding.leaderboardSwipeRefresh.setOnRefreshListener { homeViewModel.loadLeaderboard() }

        binding.leaderboardRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.leaderboardRecyclerview.adapter = LeaderboardRecyclerViewAdapter(requireContext(), leaderboard)

        homeViewModel.leaderboard.observe(viewLifecycleOwner) { leaderboardData ->
            binding.leaderboardSwipeRefresh.isRefreshing = false

            leaderboard.clear()
            for (user in leaderboardData) {
                leaderboard.add(user)
            }
            binding.leaderboardRecyclerview.adapter?.notifyDataSetChanged()

            if (leaderboardData.size >= 1) {
                binding.leaderboardTextName1.text = leaderboardData[0].name
                binding.leaderboardTextAmount1.text = "${leaderboardData[0].donatedAmount}€"

                setPhoto(requireContext(), binding.leaderboardPhoto1, leaderboardData[0].photo, R.drawable.default_user)
            }

            if (leaderboardData.size >= 2) {
                binding.leaderboardTextName2.text = leaderboardData[1].name
                binding.leaderboardTextAmount2.text = "${leaderboardData[1].donatedAmount}€"

                setPhoto(requireContext(), binding.leaderboardPhoto2, leaderboardData[1].photo, R.drawable.default_user)
            }

            if (leaderboardData.size >= 3) {
                binding.leaderboardTextName3.text = leaderboardData[2].name
                binding.leaderboardTextAmount3.text = "${leaderboardData[2].donatedAmount}€"

                setPhoto(requireContext(), binding.leaderboardPhoto3, leaderboardData[2].photo, R.drawable.default_user)
            }
        }

        addGradientToTextView(binding.leaderboardTextName1, "#FFD600", "#FF9900")
        addGradientToTextView(binding.leaderboardTextName2, "#949494", "#565656")
        addGradientToTextView(binding.leaderboardTextName3, "#8E795B", "#413A31")

        // custom recyclerview shadow
        binding.leaderboardRecyclerview.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                var rect = Rect()
                view?.background?.copyBounds(rect)
                rect.offset(0, -18)

                outline?.setRoundRect(rect, 50F)
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}