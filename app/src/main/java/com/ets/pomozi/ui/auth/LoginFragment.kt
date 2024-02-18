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
import com.ets.pomozi.databinding.FragmentLoginBinding
import com.ets.pomozi.ui.UserViewModel
import com.ets.pomozi.util.GlobalData

class LoginFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()
    private val viewModel: AuthViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.token.observe(viewLifecycleOwner) { token ->
            binding.loginProgress.visibility = View.GONE

            GlobalData.saveToken(requireContext(), token)
            userViewModel.loadUserData()
            findNavController().navigate(R.id.action_login_to_home)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            binding.loginProgress.visibility = View.GONE

            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        binding.loginButtSubmit.setOnClickListener {
            val email = binding.loginEdittextEmail.text.toString()
            val password = binding.loginEdittextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                binding.loginProgress.visibility = View.VISIBLE
                viewModel.login(email, password)
            }
        }

        binding.loginTextRegister.setOnClickListener { findNavController().navigate(R.id.action_login_to_register) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
