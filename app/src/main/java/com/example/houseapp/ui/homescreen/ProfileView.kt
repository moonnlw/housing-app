package com.example.houseapp.ui.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.houseapp.utils.AppContainer
import com.example.houseapp.MyApplication
import com.example.houseapp.R
import com.example.houseapp.databinding.FragmentProfileBinding

/**
 * Shows the profile screen
 */
class ProfileView : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var appContainer: AppContainer
    private val userViewModel: UserViewModel by activityViewModels { appContainer.userViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        appContainer = (requireActivity().application as MyApplication).appContainer

        binding = DataBindingUtil.inflate<FragmentProfileBinding?>(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = userViewModel
        }

        binding.saveButton.setOnClickListener {
            val firstName = binding.firstnameField.text.toString()
            val lastName = binding.lastnameField.text.toString()
            val address = binding.addressField.text.toString()
            val phone = binding.phoneField.text.toString()
        /*    val user = User(firstName, lastName, address, phone)
            userViewModel.saveUser(user)
            Toast.makeText(activity, "Successfully saved", Toast.LENGTH_LONG).show()
        */}

        return binding.root
    }
}

