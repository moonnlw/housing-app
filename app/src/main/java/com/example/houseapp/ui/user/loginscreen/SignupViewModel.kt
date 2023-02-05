package com.example.houseapp.ui.user.loginscreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.houseapp.data.repos.AuthRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.data.models.User
import com.example.houseapp.data.models.Response.Success
import com.example.houseapp.data.models.Response.Failure
import com.example.houseapp.ui.BaseViewModel
import kotlinx.coroutines.launch

class SignupViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    /**
     * Поля для 2-way data-binding.
     */
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
            showMessage("Please fill all the fields")
        else {
            _isLoading.value = true
            viewModelScope.launch {
                when (val result = authRepository.createUser(email, password)) {
                    is Success -> addToDatabase(result.data)
                    is Failure -> {
                        showMessage("Error occurred, please, try again")
                        Log.e(this.javaClass.simpleName, result.ex.message.toString())
                    }
                }
                _isLoading.postValue(false)
            }
        }
    }

    /**
     * Добавление пользователя в локальную и удаленную бд методом userRepository.addUser()
     * + навигация в случае успеха
     */
    private suspend fun addToDatabase(user: User) {
        when (val result = userRepository.addUser(user)) {
            is Success -> {
                showMessage("Successfully Registered")
                navigate(SignupFragmentDirections.navigateToUserActivity())
            }
            is Failure -> {
                deleteUserFirebase()
                showMessage("Error occurred, please, try again later")
                Log.e(this.javaClass.simpleName, result.ex.message.toString())
            }
        }
    }

    /**
     * Удаление текущего пользователя из Firebase.
     */
    private suspend fun deleteUserFirebase() {
        when (val result = authRepository.deleteUser()) {
            is Success -> Log.w(
                this.javaClass.simpleName,
                "Successfully deleted user from Firebase"
            )
            is Failure -> Log.e(this.javaClass.simpleName, result.ex.message.toString())
        }
    }

    fun navigateToLogin() {
        navigate(SignupFragmentDirections.navigateToLoginFragment())
    }
}