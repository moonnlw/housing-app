package com.example.houseapp.ui.user.loginscreen

import android.util.Log
import androidx.lifecycle.*
import com.example.houseapp.data.repos.AuthRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.data.models.Response.Success
import com.example.houseapp.data.models.Response.Failure
import com.example.houseapp.ui.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val userIdFlow: Flow<String?> = authRepository.observeUserId()

    val userId = authRepository.getUserId()

    /**
     * Пользователь авторизован если [authRepository].userId станет не равен null && появятся записи в Room
     */
    private val isAuthorized: Flow<Boolean> = userIdFlow.mapLatest {
        it != null && !userRepository.isUserDatabaseEmpty()
    }

    /**
     * Поля для 2-way data-binding
     */
    val userEmail = MutableLiveData("")
    val userPassword = MutableLiveData("")

    /**
     * Инициализация: проверка на авторизацию пользователя
     * Если первое значение испускаемое isAuthorized : Flow<Boolean> == true значит пользователь авторизован
     */
    init {
        _isLoading.value = true

        viewModelScope.launch {
            if (isAuthorized.first()) {
                finishAuth()
            } else {
                _isLoading.postValue(false)
            }
        }
    }

    /**
     * Срабатывает при нажатии на кнопку login (data-binding)
     * Валидация опреации логина
     */
    fun loginUser() {
        val email = userEmail.value
        val password = userPassword.value

        if (email.isNullOrBlank() || password.isNullOrBlank()) {
            showMessage("Please fill all the fields")
        } else {
            _isLoading.value = true

            viewModelScope.launch {
                when (val result = authRepository.signIn(email, password)) {
                    is Success -> loadData()
                    is Failure -> {
                        showMessage("Error occurred, please, try again later")
                        Log.e(javaClass.simpleName, result.ex.message.toString())
                        _isLoading.postValue(false)
                    }
                }
            }
        }
    }

    /**
     * Подгружает данные о пользователе/пользователях из БД
     */
    private suspend fun loadData() {
        val userId = authRepository.getUserId()!!
        if (userRepository.isUserAdmin(userId)) refreshAllUsersInfo() else refreshUserInfo(userId)
    }

    /**
     * Обновление данных одного пользователя и обработка запроса
     */
    private suspend fun refreshUserInfo(id: String) {
        Log.e(this.javaClass.simpleName, "refreshing info")
        when (val result = userRepository.refreshUser(id)) {
            is Success -> {
                Log.i(this.javaClass.simpleName, "User info is refreshed")
                finishAuth()
            }
            is Failure -> {
                showMessage("Error occurred, please, try again later")
                Log.e(this.javaClass.simpleName, result.ex.message.toString())
            }
        }
    }

    /**
     * Обновление данных всех пользователя и обработка запроса (для админа)
     */
    private suspend fun refreshAllUsersInfo() {
        when (val result = userRepository.refreshAllUsers()) {
            is Success -> {
                Log.i(this.javaClass.simpleName, "All users are refreshed")
                finishAuth()
            }
            is Failure -> {
                showMessage("Error occurred, please, try again later")
                Log.e(this.javaClass.simpleName, result.ex.message.toString())
            }
        }
    }

    /**
     * Заканчивает авторизацию, проверет роль пользователя и производит навигацию в соответсвующее
     * Activity
     */
    private fun finishAuth() {
        viewModelScope.launch {
            val userId = authRepository.getUserId()!!

            if (userRepository.isUserAdmin(userId)) {
                navigate(LoginFragmentDirections.navigateToAdminActivity())
            } else {
                Log.e(this.javaClass.simpleName, "Auth is finished")
                navigate(LoginFragmentDirections.navigateToUserActivity())
            }
        }
    }

    fun navigateToSignup() {
        navigate(LoginFragmentDirections.navigateToSignupFragment())
    }
}