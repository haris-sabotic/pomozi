package com.ets.pomozi.ui.edit_profile

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ets.pomozi.R
import com.ets.pomozi.api.requests.EditUserRequest
import com.ets.pomozi.databinding.FragmentEditProfileBinding
import com.ets.pomozi.ui.UserViewModel
import com.ets.pomozi.util.setPhoto
import java.io.ByteArrayOutputStream

class EditProfileFragment : Fragment() {
    private val userViewModel: UserViewModel by viewModels()
    private val args: EditProfileFragmentArgs by navArgs()

    private var selectedPhotoString: String? = null

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)

            selectedPhotoString = Base64.encodeToString(
                getBytesFromBitmap(bitmap),
                Base64.NO_WRAP
            )

            Glide.with(requireContext())
                .asBitmap()
                .load(bitmap)
                .into(binding.editProfilePhoto);
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    private var _binding: FragmentEditProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userData = args.userData

        setPhoto(requireContext(), binding.editProfilePhoto, userData.photo, R.drawable.default_user)
        binding.editProfileEdittextName.setText(userData.name)
        binding.editProfileEdittextEmail.setText(userData.email)

        userViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        userViewModel.userData.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_edit_profile_to_profile)
        }

        binding.editProfileIconEdit.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.editProfileButtSave.setOnClickListener {
            val photo = selectedPhotoString
            val name = binding.editProfileEdittextName.text.toString()
            val email = binding.editProfileEdittextEmail.text.toString()
            val password = binding.editProfileEdittextPassword.text.toString()

            val request = EditUserRequest(name, email, password, null, null, photo)
            userViewModel.editUser(request)
        }
    }

    private fun getBytesFromBitmap(bitmap: Bitmap): ByteArray? {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
        return stream.toByteArray()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
