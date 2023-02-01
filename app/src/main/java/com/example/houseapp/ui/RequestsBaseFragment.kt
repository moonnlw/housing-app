package com.example.houseapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.houseapp.MyApplication
import com.example.houseapp.R
import com.example.houseapp.ui.adapters.ItemClickListener
import com.example.houseapp.ui.adapters.RequestAdapter
import com.example.houseapp.ui.adapters.SpaceItemDecorator
import com.example.houseapp.utils.AppContainer

open class RequestsBaseFragment<VBinding : ViewDataBinding>
    (@LayoutRes private val layoutResId: Int) : Fragment() {

    protected lateinit var appContainer: AppContainer

    protected var viewModelAdapter: RequestAdapter? = null

    private var _binding: VBinding? = null
    protected val binding: VBinding
        get() = _binding!!

    open fun VBinding.initialize() {}
    open fun addAppBarMenu() {}
    open fun observeData() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appContainer = (requireActivity().application as MyApplication).appContainer
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)

        binding.initialize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViews()
        observeData()
        addAppBarMenu()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpViews() {
        /**
         * Создание адаптера, принимает класс RequestClickListener, блок которого выполняется при вызове метода onClick
         */
        viewModelAdapter = RequestAdapter(
            ItemClickListener {
                val bundle = bundleOf(RequestAdapter.REQUEST_KEY to it.requestId)
                findNavController().navigate(R.id.action_navigate_to_request, bundle)
            }
        )

        /**
         * Привязка RecyclerView к адаптеру, настройка RecyclerView
         */
        binding.root.findViewById<RecyclerView>(R.id.requests_list).apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = viewModelAdapter
            addItemDecoration(SpaceItemDecorator())
        }
    }
}