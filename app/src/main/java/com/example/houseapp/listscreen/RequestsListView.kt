package com.example.houseapp.listscreen

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.houseapp.R
import com.example.houseapp.UserRequest
import com.example.houseapp.UserRequests
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLException

/**
 * Показывает список запросов пользователя
 */
class RequestsListView : Fragment() {
    private val viewModel : UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_requests_list, container, false)

        val data = getUserRequests()

        val viewAdapter = RequestAdapter(data)

        view.findViewById<RecyclerView>(R.id.requests_list).run {
            setHasFixedSize(true)
            adapter = viewAdapter
            addItemDecoration(SpaceItemDecorator())
        }
        return view
    }

    private fun getUserRequests(): ArrayList<UserRequest>  {
        val currentUserId = viewModel.getUserId()
        val requests : ArrayList<UserRequest> = ArrayList()
        try {
            transaction {
                UserRequests.select(UserRequests.userId.eq(currentUserId)).forEach() {
                    requests.add(UserRequest(
                        it[UserRequests.userId],
                        it[UserRequests.problemType],
                        it[UserRequests.description],
                        it[UserRequests.isDone]
                    ))
                }
            }
        } catch (e: SQLException) {
            println(e)
            Toast.makeText(activity, "Check your Internet connection", Toast.LENGTH_LONG).show()
        }
        return requests
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

class RequestAdapter(private val requestSet: ArrayList<UserRequest>) :
    RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

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
        holder.item.findViewById<TextView>(R.id.request_text).text = item.text

        holder.item.findViewById<TextView>(R.id.request_status).text =
            if (item.isDone) "completed" else "declined"

        holder.item.setOnClickListener {
            val bundle = bundleOf(PROBLEM_KEY to item.problemType)

            holder.item.findNavController().navigate(
                R.id.action_requests_to_one,
                bundle
            )
        }
    }


    override fun getItemCount() = requestSet.size

    companion object {
        const val PROBLEM_KEY = "type"
        /*const val REQUEST_KEY = "id"
        const val ID_KEY = "id"
        const val FROM_KEY = "address"
        const val DATE_KEY = "address"
        const val ADDRESS_KEY = "address"*/
    }
}
