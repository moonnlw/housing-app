package com.example.houseapp.listscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.houseapp.R
import com.example.houseapp.listscreen.RequestAdapter.Companion.REQUEST_KEY


/**
 * Фрагмент отображает выбранную заявку из списка заявок
 */
class RequestItemView : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_request_info, container, false)

        val name = arguments?.getString(REQUEST_KEY) ?: "request_key"

        return view
    }
}
