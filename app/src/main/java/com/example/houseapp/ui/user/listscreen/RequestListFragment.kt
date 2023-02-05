package com.example.houseapp.ui.user.listscreen


import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.houseapp.R
import com.example.houseapp.ui.BaseFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.houseapp.databinding.FragmentRequestListBinding
import com.example.houseapp.ui.adapters.ItemClickListener
import com.example.houseapp.ui.adapters.RequestAdapter
import com.example.houseapp.ui.adapters.SpaceItemDecorator


/**
 * Показывает список запросов пользователя c id == [args].userId
 */
class RequestListFragment : BaseFragment<FragmentRequestListBinding, RequestListViewModel>
    (R.layout.fragment_request_list) {

    private val args by navArgs<RequestListFragmentArgs>()
    private lateinit var requestAdapter: RequestAdapter

    override val fragmentViewModel: RequestListViewModel by viewModels { appContainer.viewModelFactory }

    override fun RequestListViewModel.initialize() {
        userId.value = args.userId
    }

    override fun onReady(savedInstanceState: Bundle?) {
        setUpAdapter()
        setUpRecyclerView()
        observeData()
        addAppBarMenu()
    }

    private fun observeData() {
        fragmentViewModel.requests.observe(viewLifecycleOwner) { requests ->
            requests?.let {
                requestAdapter.submitList(requests)
            }
        }
    }

    private fun setUpAdapter() {
        requestAdapter = RequestAdapter(
            ItemClickListener {
                val userId = fragmentViewModel.userId.value
                val action =
                    RequestListFragmentDirections.actionNavigateToRequest(it.requestId!!, userId)
                findNavController().navigate(action)
            })
    }

    private fun setUpRecyclerView() {
        binding.root.findViewById<RecyclerView>(R.id.request_list).apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = requestAdapter
            addItemDecoration(SpaceItemDecorator())
        }
    }

    private fun addAppBarMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.requests_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.refresh -> {
                        fragmentViewModel.refreshRequests()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}
