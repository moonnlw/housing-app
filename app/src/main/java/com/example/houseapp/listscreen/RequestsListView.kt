package com.example.houseapp.listscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.houseapp.R
import com.example.houseapp.UserRequest
import com.example.houseapp.UserRequests
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Показывает список запросов пользователя
 */
class RequestsListView : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_requests_list, container, false)

        val data = getUserRequests()

        val viewAdapter = RequestAdapter(data)

        view.findViewById<RecyclerView>(R.id.requests_list).run {
            setHasFixedSize(true)
            adapter = viewAdapter

        }

        return view
    }

    private fun getUserRequests(): ArrayList<UserRequest>  {
        val requests : ArrayList<UserRequest> = ArrayList()
        try {

            transaction {
                addLogger()
                UserRequests.select(UserRequests.userId.eq(24)).forEach() {
                    requests.add(UserRequest(
                        it[UserRequests.userId],
                        it[UserRequests.problemType],
                        it[UserRequests.description],
                        it[UserRequests.isDone]
                    ))
                }
            }
        } catch (e: Exception) {
            println(e)
        }
        return requests
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
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_item, parent, false)

        return ViewHolder(itemView)
    }

    /**
     * Подставлет значения полей элемента Request с индексом position во View
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = requestSet[position]

        holder.item.findViewById<TextView>(R.id.request_problemType).text = item.problemType
        //holder.item.findViewById<TextView>(R.id.request_id).text = item.id.toString()
        holder.item.findViewById<TextView>(R.id.request_text).text = item.text

        holder.item.findViewById<TextView>(R.id.request_status).text =
            if (item.isDone) "OK" else "DECLINE"

        holder.item.setOnClickListener {
            val bundle = bundleOf()

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
