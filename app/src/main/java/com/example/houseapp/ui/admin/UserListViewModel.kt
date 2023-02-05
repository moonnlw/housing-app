package com.example.houseapp.ui.admin

import android.util.Log
import androidx.lifecycle.*
import com.example.houseapp.data.models.Response.Failure
import com.example.houseapp.data.models.Response.Success
import com.example.houseapp.data.models.User
import com.example.houseapp.data.repos.AuthRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.ui.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map


class UserListViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : BaseViewModel() {

    /**
     * Фильтрация пользователей c заполненной личной информацией.
     */
    val users: LiveData<List<User>> =
        userRepository.getAllUsers().map {
            it.filter { user -> isEnoughUserData(user) }
        }.asLiveData()

    init {
        refreshUsers()
    }

    fun signOutUser() = authRepository.signOut()

    /**
     * Загружает данные пользователей и обрабатывает результат
     */
    fun refreshUsers() {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = userRepository.refreshAllUsers()) {
                is Success ->
                    Log.i(this.javaClass.simpleName, "Users refreshed")
                is Failure -> {
                    Log.e(this.javaClass.simpleName, result.ex.toString())
                    showMessage("Probably, you have bad internet connection")
                }
            }
            _isLoading.postValue(false)
        }
    }

    /**
     * Проверяет, заполнены ли данные пользователя.
     */
    private fun isEnoughUserData(user: User?): Boolean {
        return user != null &&
                user.firstName.isNotBlank()
                && user.lastName.isNotBlank()
                && user.address.isNotBlank()
                && user.phone.isNotBlank()
    }
}
