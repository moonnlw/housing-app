package com.example.houseapp.ui.user.homescreen

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.houseapp.AuthActivity
import com.example.houseapp.MyApplication
import com.example.houseapp.R
import com.example.houseapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var fragmentProfileBinding: FragmentProfileBinding? = null

    private val profileViewModel: ProfileViewModel by activityViewModels {
        (requireActivity().application as MyApplication).appContainer.viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = profileViewModel
        }
        fragmentProfileBinding = binding
        addAppBarMenu()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    override fun onDestroyView() {
        fragmentProfileBinding = null
        super.onDestroyView()
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
                        profileViewModel.signOutUser()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun observeData() {
        profileViewModel.isAuthorized.observe(viewLifecycleOwner) { isAuthorized ->
            if (!isAuthorized) {
                val intent = Intent(activity, AuthActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        profileViewModel.message.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}

