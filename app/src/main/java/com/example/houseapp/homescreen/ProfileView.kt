package com.example.houseapp.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.houseapp.R
import com.example.houseapp.User
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.io.File

/**
 * Shows the profile screen
 */
class ProfileView : Fragment() {
    private lateinit var path : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val viewOfLayout = inflater.inflate(R.layout.fragment_profile, container, false)
        path = context?.filesDir?.path.toString() + "/savedData"
        val saveButton = viewOfLayout.save_button

        val file = File("$path/profile.json")
        if(file.exists()) {
            val gson = Gson()
            val user = gson.fromJson(readJsonFile(file.path), User::class.java)
            viewOfLayout.firstname_field.setText(user.firstName)
            viewOfLayout.lastname_field.setText(user.lastName)
            viewOfLayout.address_field.setText(user.address)
            viewOfLayout.phone_field.setText(user.phone)
        }

        saveButton.setOnClickListener {
            val firstName = viewOfLayout.firstname_field.text.toString()
            val lastName = viewOfLayout.lastname_field.text.toString()
            val address = viewOfLayout.address_field.text.toString()
            val phone = viewOfLayout.phone_field.text.toString()
            val user = User(firstName, lastName, address, phone)
            writeJsonFile(user, "profile.json")
            Toast.makeText(activity, "Successfully saved", Toast.LENGTH_LONG).show()
        }
        return viewOfLayout
    }

    private fun writeJsonFile(obj : Any, filename: String) {
        val gson = Gson()
        val file = File("$path/$filename")
        if (!file.exists()) {
            createFile(path, filename)
        }
        file.writeText(gson.toJson(obj))
    }

    private fun readJsonFile(filepath: String) : String {
        val bufferedReader = File(filepath).bufferedReader()
        return bufferedReader.use { it.readText() }
    }

    private fun createFile(path : String?, filename : String)  {
        val letDirectory = File(path, "/savedData")
        if(!letDirectory.exists())
            letDirectory.mkdirs()
        val file = File(letDirectory, filename)
    }
}

