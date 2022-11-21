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
import com.example.houseapp.FakeRequest
import com.example.houseapp.R

/**
 * Показывает список запросов пользователя
 */
class RequestsListView : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_requests_list, container, false)

        val data = Array(10) { FakeRequest(id = it + 1) } // data example

        val viewAdapter = RequestAdapter(data)

        view.findViewById<RecyclerView>(R.id.requests_list).run {
            setHasFixedSize(true)
            adapter = viewAdapter

        }

        return view
    }

}

class RequestAdapter(private val requestSet: Array<FakeRequest>) :
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
