package com.example.houseapp.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.houseapp.R
import com.example.houseapp.data.User
import com.example.houseapp.databinding.FragmentProfileBinding

/**
 * Shows the profile screen
 */
class ProfileView : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val userViewmodel by activityViewModels<UserViewmodel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentProfileBinding?>(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = userViewmodel
        }
        userViewmodel.restoreData(context)

        binding.saveButton.setOnClickListener {
            val firstName = binding.firstnameField.text.toString()
            val lastName = binding.lastnameField.text.toString()
            val address = binding.addressField.text.toString()
            val phone = binding.phoneField.text.toString()
            val user = User(firstName, lastName, address, phone)
            userViewmodel.saveUser(user)
            Toast.makeText(activity, "Successfully saved", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }
}

