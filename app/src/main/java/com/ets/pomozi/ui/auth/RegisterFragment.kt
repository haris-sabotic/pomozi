package com.ets.pomozi.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ets.pomozi.R
import com.ets.pomozi.databinding.FragmentRegisterBinding
import com.ets.pomozi.ui.UserViewModel
import com.ets.pomozi.util.GlobalData

class RegisterFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()
    private val viewModel: AuthViewModel by viewModels()

    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.token.observe(viewLifecycleOwner) { token ->
            binding.registerProgress.visibility = View.GONE

            GlobalData.saveToken(requireContext(), token)
            userViewModel.loadUserData()
            findNavController().navigate(R.id.action_register_to_home)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            binding.registerProgress.visibility = View.GONE

            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        binding.registerButtSubmit.setOnClickListener {
            val name = binding.registerEdittextName.text.toString()
            val email = binding.registerEdittextEmail.text.toString()
            val password = binding.registerEdittextPassword.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                binding.registerProgress.visibility = View.VISIBLE
                viewModel.register(name, email, password)
            }
        }

        binding.registerButtBack.setOnClickListener { findNavController().navigate(R.id.action_register_to_slider1) }
        binding.registerTextLogin.setOnClickListener { findNavController().navigate(R.id.action_register_to_login) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
