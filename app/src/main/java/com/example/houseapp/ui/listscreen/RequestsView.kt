package com.example.houseapp.ui.listscreen

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.houseapp.utils.AppContainer
import com.example.houseapp.MyApplication
import com.example.houseapp.R
import com.example.houseapp.data.models.UserRequest
import com.example.houseapp.databinding.FragmentRequestsListBinding
import com.example.houseapp.databinding.ListViewItemBinding
import com.example.houseapp.ui.listscreen.RequestAdapter.Companion.REQUEST_KEY

/**
 * Показывает список запросов пользователя
 */
class RequestsListView : Fragment() {

    private var viewModelAdapter: RequestAdapter? = null
    private lateinit var appContainer: AppContainer
    private lateinit var binding: FragmentRequestsListBinding
    private val requestsViewModel: RequestsViewModel by activityViewModels { appContainer.requestsViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        appContainer = (requireActivity().application as MyApplication).appContainer

        binding = DataBindingUtil.inflate<FragmentRequestsListBinding>(
            inflater,
            R.layout.fragment_requests_list,
            container,
            false
        ).apply {
            // Устанавливаем lifecycleOwner, чтобы binding мог прослушивать LiveData
            lifecycleOwner = viewLifecycleOwner
            viewModel = requestsViewModel
        }

        /**
         * Создание адаптера, принимает класс RequestClickListener, блок которого выполняется при вызове метода onClick
         */
        viewModelAdapter = RequestAdapter(
            RequestClickListener {
                val bundle = bundleOf(REQUEST_KEY to it.requestId)
                findNavController().navigate(R.id.action_requests_to_one, bundle)
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * Прослушивание LiveData из requestsViewModel и привязка к адаптеру
         */
        requestsViewModel.requests.observe(viewLifecycleOwner) { requests ->
            requests?.apply {
                viewModelAdapter?.requestSet = requests
            }
        }

        /**
         * Добавляем кастомное меню
         */
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

/**
 * Класс устанавливает индивидуальные правила отрисовки элементов RecycleView
 */
class SpaceItemDecorator : RecyclerView.ItemDecoration() {

    /**
     * Устанавливает отдельный отступ для первого элемента в списке
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val margin = 10
        val space = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            margin.toFloat(),
            view.resources.displayMetrics
        )

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space.toInt()
            outRect.bottom = 0
        }
    }
}

/**
 * Класс для обработки нажатия на элемент адаптера
 */
class RequestClickListener(val block: (UserRequest) -> Unit) {
    fun onClick(request: UserRequest) = block(request)
}

/**
 * Адаптер применяет data binding к каждому элементу requestSet
 */
class RequestAdapter(private val callback: RequestClickListener) :
    RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

    var requestSet: List<UserRequest> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val viewDataBinding: ListViewItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    /**
     * Создает ViewHolder
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ListViewItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_view_item, parent, false
        )
        return ViewHolder(binding)
    }

    /**
     * Передает UserRequest с индексом position в переменные list_item_view.xml, тем самым обновляет их содержимое
     */
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.request = requestSet[position]
            it.requestCallback = callback
        }
    }

    override fun getItemCount() = requestSet.size

    companion object {
        const val REQUEST_KEY = "id"
    }
}
