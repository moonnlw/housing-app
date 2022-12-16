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
            get(value)
        }

    val user: LiveData<User?>
        get() = _user

    private val _user = MutableLiveData<User?>()

    private fun get(userId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            _user.value = userRepository.getUser(userId)
        }
    }
}