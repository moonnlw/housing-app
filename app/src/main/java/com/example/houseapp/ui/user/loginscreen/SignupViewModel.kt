package com.example.houseapp.ui.user.loginscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houseapp.data.repos.AuthRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.data.models.User
import com.example.houseapp.data.models.Event
import com.example.houseapp.data.models.Response.Success
import com.example.houseapp.data.models.Response.Failure
import kotlinx.coroutines.launch

class SignupViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val statusMessage = MutableLiveData<Event<String>>()
    private val _isLoading = MutableLiveData(false)
    private val _isRegistered = MutableLiveData(false)

    val isLoading: LiveData<Boolean> get() = _isLoading
    val message: LiveData<Event<String>> get() = statusMessage
    val isRegistered: LiveData<Boolean> get() = _isRegistered

    val userEmail = MutableLiveData("")
    val userPassword = MutableLiveData("")

    /**
     * Метод регистрирует пользователя:
     * в случае успеха createUserFirebase() добавляет пользователя в бд вызовом метода
     * addToDatabase()
     */
    fun registerUser() {
        val email = userEmail.value!!
        val password = userPassword.value!!

        if (email.isEmpty() || password.isEmpty())
            statusMessage.value = Event("Please fill all the fields")
        else {
            _isLoading.value = true
            viewModelScope.launch {
                when (val result = authRepository.createUser(email, password)) {
                    is Success -> addToDatabase(result.data)
                    is Failure -> {
                        statusMessage.postValue(Event("Error occurred, please, try again"))
                        Log.e(this.javaClass.simpleName, result.ex.message.toString())
                    }
                }
                _isLoading.postValue(false)
            }
        }
    }

    /**
     * Добавление пользователя в локальную и удаленную бд методом userRepository.addUser()
     */
    private suspend fun addToDatabase(user: User) {
        when (val result = userRepository.addUser(user)) {
            is Success -> {
                statusMessage.postValue(Event("Successfully Registered"))
                _isRegistered.postValue(true)
            }
            is Failure -> {
                deleteUserFirebase()
                statusMessage.postValue(Event("Error occurred, please, try again later"))
                Log.e(this.javaClass.simpleName, result.ex.message.toString())
            }
        }
    }

    /**
     * Удаление текущего пользователя из Firebase
     */
    private suspend fun deleteUserFirebase() {
        when (val result = authRepository.deleteUser()) {
            is Success -> Log.w(this.javaClass.simpleName, "Successfully deleted user from Firebase")
            is Failure -> Log.e(this.javaClass.simpleName, result.ex.message.toString())
        }
    }
}