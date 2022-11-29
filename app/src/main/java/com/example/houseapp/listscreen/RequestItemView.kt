package com.example.houseapp.listscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.houseapp.R
import com.example.houseapp.listscreen.RequestAdapter.Companion.PROBLEM_KEY


/**
 * Фрагмент отображает выбранную заявку из списка заявок
 */
class RequestItemView : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_request_info, container, false)

        val requestType = (requireArguments().getString(PROBLEM_KEY))

        view.findViewById<TextView>(R.id.info_problemType).text = requestType
        return view
    }
}
