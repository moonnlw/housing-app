package com.example.houseapp.ui.user.loginscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houseapp.data.repos.AuthRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.data.models.Event
import com.example.houseapp.data.models.Response.Success
import com.example.houseapp.data.models.Response.Failure
import kotlinx.coroutines.launch


class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val statusMessage = MutableLiveData<Event<String>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _isAuthorized = MutableLiveData<Boolean>()

    val message: LiveData<Event<String>> get() = statusMessage
    val isLoading: LiveData<Boolean> get() = _isLoading
    val isAuthorized: LiveData<Boolean> get() = _isAuthorized

    val userEmail = MutableLiveData("")
    val userPassword = MutableLiveData("")

    fun loginUser() {
        val email = userEmail.value
        val password = userPassword.value

        if (email.isNullOrBlank() || password.isNullOrBlank()) {
            statusMessage.value = Event("Please fill all the fields")
        } else {
            _isLoading.value = true
            viewModelScope.launch {
                when (val result = authRepository.signIn(email, password)) {
                    is Success -> refreshUserInfo(result.data.id)
                    is Failure -> {
                        statusMessage.postValue(Event("Error occurred, please, try again later"))
                        Log.e(javaClass.simpleName, result.ex.message.toString())
                        _isLoading.postValue(false)
                    }
                }
            }
        }
    }

    private suspend fun refreshUserInfo(id: String) {
        when (val result = userRepository.refreshUser(id)) {
            is Success -> _isAuthorized.postValue(true)
            is Failure -> {
                statusMessage.postValue(Event("Error occurred, please, try again later"))
                Log.e(this.javaClass.simpleName, result.ex.message.toString())
                _isLoading.postValue(false)
            }
        }
    }
}