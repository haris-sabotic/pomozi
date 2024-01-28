package com.ets.pomozi.ui.slider

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ets.pomozi.R
import com.ets.pomozi.databinding.FragmentSlider3Binding

class Slider3Fragment : Fragment() {

    private var _binding: FragmentSlider3Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var canSlide = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlider3Binding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            if (canSlide) {
                findNavController().navigate(R.id.action_slider3_to_slider1)
            }
        }, 2000)

        binding.slider3ButtonLogin.setOnClickListener { findNavController().navigate(R.id.action_slider3_to_login) }
        binding.slider3ButtonRegister.setOnClickListener { findNavController().navigate(R.id.action_slider3_to_register) }
    }

    override fun onDestroyView() {
        canSlide = false
        super.onDestroyView()
        _binding = null
    }
}
