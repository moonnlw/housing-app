package com.example.houseapp.loginscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.houseapp.R

/**
 * Вход в приложение
 */
class LoginView : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.login).setOnClickListener {
            /*val emailText = view.findViewById<EditText>(R.id.email_text).text
            val passwordText = view.findViewById<EditText>(R.id.password_text).text

            if (emailText.isEmpty() && passwordText.isEmpty()) */
            findNavController().navigate(R.id.action_login_to_home)
        }
    }
}