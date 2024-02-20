package com.ets.pomozi.ui.home

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
import com.bumptech.glide.Glide
import com.ets.pomozi.R
import com.ets.pomozi.databinding.FragmentLeaderboardBinding
import com.ets.pomozi.databinding.FragmentOrganizationsBinding
import com.ets.pomozi.models.OrganizationModel
import com.ets.pomozi.models.UserModel
import com.ets.pomozi.ui.OrganizationViewModel
import com.ets.pomozi.ui.rewards.RewardsFragmentDirections
import com.ets.pomozi.ui.rewards.RewardsRecyclerViewAdapter
import com.ets.pomozi.util.GlobalData
import com.ets.pomozi.util.addGradientToTextView
import com.ets.pomozi.util.setPhoto

class OrganizationsFragment : Fragment() {
    val organizationsViewModel: OrganizationViewModel by activityViewModels()

    private var _binding: FragmentOrganizationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var organizations = arrayListOf<OrganizationModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrganizationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addGradientToTextView(binding.organizationsTextDonate, "#FE724D", "#FFC529")

        binding.organizationsArrowBack.setOnClickListener { findNavController().popBackStack() }

        organizationsViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        organizationsViewModel.loadOrganizations("")
        binding.organizationsSwipeRefresh.isRefreshing = true

        binding.organizationsSwipeRefresh.setOnRefreshListener {
            val text: String = binding.organizationsEdittextSearch.text.toString()
            organizationsViewModel.loadOrganizations(text.trim())
        }

        binding.organizationsRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.organizationsRecyclerview.adapter = OrganizationsRecyclerViewAdapter(requireContext(), organizations)

        binding.organizationsEdittextSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text: String = binding.organizationsEdittextSearch.text.toString()
                organizationsViewModel.loadOrganizations(text.trim())
                binding.organizationsSwipeRefresh.isRefreshing = true
                return@setOnEditorActionListener true
            }

            false
        }

        organizationsViewModel.organizations.observe(viewLifecycleOwner) {
            binding.organizationsSwipeRefresh.isRefreshing = false
            organizations.clear()
            for (org in it) {
                organizations.add(org)
            }

            binding.organizationsRecyclerview.adapter?.notifyDataSetChanged()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}