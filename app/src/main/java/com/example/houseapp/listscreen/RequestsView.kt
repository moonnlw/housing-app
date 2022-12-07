package com.example.houseapp.listscreen

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.houseapp.AppContainer
import com.example.houseapp.MyApplication
import com.example.houseapp.R
import com.example.houseapp.data.UserRequest

/**
 * Показывает список запросов пользователя
 */
class RequestsListView : Fragment() {

    private val viewAdapter: RequestAdapter = RequestAdapter()

    private lateinit var appContainer: AppContainer
    private val viewModel: RequestsViewModel by activityViewModels { appContainer.requestsViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_requests_list, container, false)

        view.findViewById<RecyclerView>(R.id.requests_list).run {
            setHasFixedSize(true)
            adapter = viewAdapter
            addItemDecoration(SpaceItemDecorator())
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appContainer = (requireActivity().application as MyApplication).appContainer

        viewModel.requests.observe(viewLifecycleOwner) { requests ->
            requests?.apply {
                viewAdapter.requestSet = requests
            }
        }
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
        val margin = 12
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


class RequestAdapter :
    RecyclerView.Adapter<RequestAdapter.ViewHolder>() {


    var requestSet: List<UserRequest> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     * Класс хранит ссылки на view для каждого элемента данных
     */
    class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)


    /**
     * Создает views
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_item, parent, false)

        return ViewHolder(itemView)
    }

    /**
     * Подставлет значения полей элемента Request с индексом position во View
     */
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = requestSet[position]

        holder.item.findViewById<TextView>(R.id.request_problemType).text = item.problemType
        holder.item.findViewById<TextView>(R.id.request_text).text = item.description
        holder.item.findViewById<TextView>(R.id.request_status).text =
            if (item.isDone) "done" else "in progress"

        holder.item.setOnClickListener {
            val bundle = bundleOf(REQUEST_KEY to item.requestId)

            holder.item.findNavController().navigate(
                R.id.action_requests_to_one,
                bundle
            )
        }
    }


    override fun getItemCount() = requestSet.size

    companion object {
        const val REQUEST_KEY = "id"
    }
}
