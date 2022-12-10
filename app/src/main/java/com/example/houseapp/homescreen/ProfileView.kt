package com.example.houseapp.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.houseapp.R
import com.example.houseapp.data.User
import com.example.houseapp.databinding.FragmentProfileBinding
import com.google.gson.Gson
import java.io.File

/**
 * Shows the profile screen
 */
class ProfileView : Fragment() {

    private lateinit var user: User
    private lateinit var path: String
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )
        restoreData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            val firstName = binding.firstnameField.text.toString()
            val lastName = binding.lastnameField.text.toString()
            val address = binding.addressField.text.toString()
            val phone = binding.phoneField.text.toString()
            val user = User(firstName, lastName, address, phone)
            writeJsonFile(user, "profile.json")
            Toast.makeText(activity, "Successfully saved", Toast.LENGTH_LONG).show()
        }
    }

    private fun restoreData() {
        path = context?.filesDir?.path.toString() + "/savedData"

        val file = File("$path/profile.json")
        if (file.exists()) {
            val gson = Gson()
            user = gson.fromJson(readJsonFile(file.path), User::class.java)
            binding.user = user
        }
    }

    private fun writeJsonFile(obj: Any, filename: String) {
        val gson = Gson()
        val file = File("$path/$filename")
        if (!file.exists()) {
            createFile(path, filename)
        }
        file.writeText(gson.toJson(obj))
    }

    private fun readJsonFile(filepath: String): String {
        val bufferedReader = File(filepath).bufferedReader()
        return bufferedReader.use { it.readText() }
    }

    private fun createFile(path: String?, filename: String) {
        val letDirectory = File(path, "/savedData")
        if (!letDirectory.exists())
            letDirectory.mkdirs()
        val file = File(letDirectory, filename)
    }
}

