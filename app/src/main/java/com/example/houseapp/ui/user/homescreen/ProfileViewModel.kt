package com.example.houseapp.ui.user.homescreen

import android.util.Log
import androidx.lifecycle.*
import com.example.houseapp.data.models.Event
import com.example.houseapp.data.models.Response.Success
import com.example.houseapp.data.models.Response.Failure
import com.example.houseapp.data.models.User
import com.example.houseapp.data.repos.AuthRepository
import com.example.houseapp.data.repos.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val userId: Flow<String?> = authRepository.observeUserId()
    private val statusMessage = MutableLiveData<Event<String>>()
    private val _isLoading = MutableLiveData(false)

    val message: LiveData<Event<String>> get() = statusMessage
    val isLoading: LiveData<Boolean> get() = _isLoading

    val user: LiveData<User?> = userId.flatMapLatest { id ->
        userRepository.getUser(id)
    }.asLiveData()

    val isAuthorized: LiveData<Boolean> =
        authRepository.observeUserId().mapLatest { id ->
            id != null
        }.asLiveData()

    fun saveUserInfo() {
        val newUserInfo = user.value
        if (!isDataValid(newUserInfo)) {
            statusMessage.value = Event("Please, fill in all fields before saving")
        } else {
            _isLoading.value = true
            viewModelScope.launch {
                when (val result = userRepository.updateUser(newUserInfo!!)) {
                    is Success -> {
                        statusMessage.postValue(Event("Info is successfully saved"))
                        _isLoading.postValue(false)
                    }
                    is Failure -> {
                        statusMessage.postValue(Event("Error occurred, try again later"))
                        _isLoading.postValue(false)
                        Log.e(this.javaClass.simpleName, result.ex.message.toString())
                    }
                }
            }
        }
    }

    fun signOutUser() = authRepository.signOut()

    private fun isDataValid(user: User?): Boolean {
        Log.i(this.javaClass.simpleName, user.toString())
        return ((user != null)
                && user.firstName.isNotBlank()
                && user.lastName.isNotBlank()
                && user.address.isNotBlank()
                && user.phone.isNotBlank())
    }
}