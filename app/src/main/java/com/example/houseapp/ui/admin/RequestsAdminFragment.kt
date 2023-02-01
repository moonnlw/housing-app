package com.example.houseapp.ui.admin

import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.houseapp.R
import com.example.houseapp.databinding.FragmentRequestsListAdminBinding
import com.example.houseapp.ui.RequestsBaseFragment


/**
 * Показывает список запросов пользователя
 */
class RequestsAdminFragment :
    RequestsBaseFragment<FragmentRequestsListAdminBinding>(R.layout.fragment_requests_list_admin) {

    private val requestsViewModel: RequestsAdminViewModel by activityViewModels { appContainer.viewModelFactory }

    override fun FragmentRequestsListAdminBinding.initialize() {
        this.lifecycleOwner = viewLifecycleOwner
        this.viewModel = requestsViewModel
    }

    override fun observeData() {
        requestsViewModel.requests.observe(viewLifecycleOwner) { requests ->
            requests?.let {
                viewModelAdapter?.submitList(it)
            }
        }
    }

    override fun addAppBarMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.requests_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.refresh -> {
                        requestsViewModel.refreshRequests()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}
