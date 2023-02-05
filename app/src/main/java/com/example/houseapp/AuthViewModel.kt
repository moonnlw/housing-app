package com.example.houseapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.houseapp.data.repos.AuthRepository
import com.example.houseapp.ui.BaseViewModel

class AuthViewModel(
    authRepository: AuthRepository
) : BaseViewModel() {

    val userId: String? = authRepository.getUserId()
    val userIdLiveData: LiveData<String?> = authRepository.observeUserId().asLiveData()
}
