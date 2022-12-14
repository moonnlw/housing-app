package com.example.houseapp.homescreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.houseapp.data.User
import com.google.gson.Gson
import java.io.File

class UserViewmodel : ViewModel() {

    private val profileFilename: String = "profile.json"
    private lateinit var filePath: String

    val user: LiveData<User?>
        get() = _user

    private val _user = MutableLiveData<User?>()

    fun restoreData(context: Context?) {
        filePath = context!!.filesDir?.path.toString() + "/savedData"
        val file = File("${filePath}/${profileFilename}")
        if (file.exists()) {
            val gson = Gson()
            _user.value = gson.fromJson(readJsonFile(file.path), User::class.java)
        }
    }

    fun saveUser(userObject: User) {
        _user.value = userObject
        writeJsonFile(userObject, profileFilename)
    }

    private fun writeJsonFile(obj: Any, filename: String) {
        val gson = Gson()
        val file = File("$filePath/$filename")
        if (!file.exists()) {
            createFile(filePath, filename)
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