package com.example.houseapp.ui.admin

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.houseapp.R
import com.example.houseapp.databinding.FragmentUserListBinding
import com.example.houseapp.ui.BaseFragment
import com.example.houseapp.ui.adapters.ItemClickListener
import com.example.houseapp.ui.adapters.SpaceItemDecorator
import com.example.houseapp.ui.adapters.UserAdapter


/**
 * Показывает список запросов пользователя
 */
class UserListFragment :
    BaseFragment<FragmentUserListBinding, UserListViewModel>
        (R.layout.fragment_user_list) {

    private lateinit var userAdapter: UserAdapter

    override val fragmentViewModel: UserListViewModel by viewModels { appContainer.viewModelFactory }

    override fun onReady(savedInstanceState: Bundle?) {
        setUpAdapter()
        setUpRecyclerView()
        observeUserList()
        addAppBarMenu()
    }

    private fun observeUserList() {
        fragmentViewModel.users.observe(viewLifecycleOwner) { users ->
            users?.let {
                userAdapter.submitList(it)
            }
        }
    }

    private fun setUpAdapter() {
        userAdapter = UserAdapter(
            ItemClickListener {
                val action = UserListFragmentDirections.actionUsersListToRequestsList(userId = it.id)
                findNavController().navigate(action)
            }
        )
    }

    private fun setUpRecyclerView() {
        binding.root.findViewById<RecyclerView>(R.id.user_list).apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userAdapter
            addItemDecoration(SpaceItemDecorator())
        }
    }

    private fun addAppBarMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.users_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.refresh -> {
                        fragmentViewModel.refreshUsers()
                        true
                    }
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
