package com.example.houseapp.ui.newrequestscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.houseapp.R

/**
 * Экран отображения статуса отправки заявки
 */

class SentView : Fragment() {
    class Registered : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {

            return inflater.inflate(R.layout.fragment_request_sent, container, false)
        }
    }

}