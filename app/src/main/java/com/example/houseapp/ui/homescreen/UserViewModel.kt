package com.example.houseapp.ui.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houseapp.data.UserRepository
import com.example.houseapp.data.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    var userId: String = ""
        set(value) {
            field = value
            refreshUser(value)
        }

    val user: LiveData<User?>
        get() = _user

    private var _user = MutableLiveData<User>(null)

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isLoading = MutableLiveData(false)

    private fun refreshUser(id: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.Default) {
            _user = userRepository.getUser(userId) as MutableLiveData<User>
            userRepository.refreshUser(id)
            _isLoading.postValue(false)
        }
    }

    fun update(user: User) {
        viewModelScope.launch(Dispatchers.Default) {
            userRepository.update(user)
        }
    }
}