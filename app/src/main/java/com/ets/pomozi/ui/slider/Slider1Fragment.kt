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
import com.ets.pomozi.databinding.FragmentSlider1Binding

class Slider1Fragment : Fragment() {

    private var _binding: FragmentSlider1Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var canSlide = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlider1Binding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            if (canSlide && findNavController().currentDestination?.id == R.id.navigation_slider1) {
                findNavController().navigate(R.id.action_slider1_to_slider2)
            }
        }, 5000)

        binding.slider1OverlayLeft.setOnClickListener { findNavController().navigate(R.id.action_slider1_to_slider3) }
        binding.slider1OverlayRight.setOnClickListener { findNavController().navigate(R.id.action_slider1_to_slider2) }
    }

    override fun onDestroyView() {
        canSlide = false
        super.onDestroyView()
        _binding = null
    }
}
