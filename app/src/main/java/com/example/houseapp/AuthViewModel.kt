package com.example.houseapp

import androidx.lifecycle.*
import com.example.houseapp.data.repos.AuthRepository
import com.example.houseapp.data.repos.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AuthViewModel(
    authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val userId = authRepository.getUserId()
    private val _isAuthorized = MutableLiveData<Boolean>()

    val isAuthorized: LiveData<Boolean> get() = _isAuthorized
    var isAdmin: Boolean = false

    /**
     * Проверка на авторизацию и на роль пользователя (админ / юзер).
     */
    init {
        viewModelScope.launch(Dispatchers.Default) {
            val authorized = isUserAuthorized(userId)
            if (authorized) {
                isAdmin = userRepository.isUserAdmin(userId!!)
            }
            _isAuthorized.postValue(authorized)
        }
    }

    /**
     * Проверяет авторизован ли пользователь && есть ли запись о пользователе в Room.
     * Запись в Room отсутствует только в случае, если вся база пуста.
     */
    private suspend fun isUserAuthorized(id: String?): Boolean =
        id != null
                && !userRepository.isUserDatabaseEmpty()
}