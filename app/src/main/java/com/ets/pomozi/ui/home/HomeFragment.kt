package com.ets.pomozi.ui.home

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ets.pomozi.R
import com.ets.pomozi.databinding.FragmentHomeBinding
import com.ets.pomozi.models.OrganizationModel
import com.ets.pomozi.ui.UserViewModel
import com.ets.pomozi.util.setPhoto


class HomeFragment : Fragment() {
    val userViewModel: UserViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var organizations = arrayListOf<OrganizationModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup user photo
        userViewModel.userData.value?.let { setPhoto(requireContext(), binding.registerHeaderPhoto, it.photo, R.drawable.default_user) }
        userViewModel.userData.observe(viewLifecycleOwner) { setPhoto(requireContext(), binding.registerHeaderPhoto, it.photo, R.drawable.default_user) }

        // Add gradient to title
        val paint: TextPaint = binding.registerTextDonate.paint
        val width: Float = paint.measureText(binding.registerTextDonate.text.toString())
        val colors = intArrayOf(Color.parseColor("#FE724D"), Color.parseColor("#FFC529"));
        val textShader: Shader = LinearGradient(0F, 0F, width, binding.registerTextDonate.textSize, colors, null, Shader.TileMode.CLAMP)
        binding.registerTextDonate.paint.setShader(textShader)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}