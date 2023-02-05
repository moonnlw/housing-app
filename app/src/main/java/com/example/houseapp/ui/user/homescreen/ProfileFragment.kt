package com.example.houseapp.ui.user.homescreen

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.example.houseapp.R
import com.example.houseapp.databinding.FragmentProfileBinding
import com.example.houseapp.ui.BaseFragment


class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {

    private val args by navArgs<ProfileFragmentArgs>()

    override val fragmentViewModel: ProfileViewModel by viewModels { appContainer.viewModelFactory }

    override fun ProfileViewModel.initialize() {
        userIdFlow.value = args.userId
    }

    override fun onReady(savedInstanceState: Bundle?) {
        addAppBarMenu()
    }

    private fun addAppBarMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.profile_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.logout -> {
                        fragmentViewModel.signOutUser()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}

