package com.example.houseapp.ui.user.homescreen

import android.util.Log
import androidx.lifecycle.*
import com.example.houseapp.data.models.Response.Success
import com.example.houseapp.data.models.Response.Failure
import com.example.houseapp.data.models.User
import com.example.houseapp.data.repos.AuthRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.ui.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val userIdFlow: MutableStateFlow<String?> = MutableStateFlow("")

    /**
     * Выпускание значения userIdFlow триггерит получение пользователя с новым id
     */
    val user: LiveData<User?> = userIdFlow.flatMapLatest { id ->
        userRepository.getUser(id)
    }.asLiveData()

    fun signOutUser() = authRepository.signOut()

    /**
     * Срабатывает при нажатии на кнопку save (data-binding),
     * Валидация данных пользователя и обработка запроса
     */
    fun saveUserInfo() {
        val newUserInfo = user.value
        if (!isDataValid(newUserInfo)) {
            Log.i(this.javaClass.simpleName, newUserInfo.toString())
            showMessage("Please, fill in all fields before saving")
        } else {
            _isLoading.value = true
            viewModelScope.launch {
                when (val result = userRepository.updateUser(newUserInfo!!)) {
                    is Success -> {
                        showMessage("Info is successfully saved")
                    }
                    is Failure -> {
                        showMessage("Error occurred, try again later")
                        Log.e(this.javaClass.simpleName, result.ex.message.toString())
                    }
                }
                _isLoading.postValue(false)
            }
        }
    }

    private fun isDataValid(user: User?): Boolean {
        Log.i(this.javaClass.simpleName, user.toString())
        return ((user != null)
                && user.firstName.isNotBlank()
                && user.lastName.isNotBlank()
                && user.address.isNotBlank()
                && user.phone.isNotBlank())
    }
}