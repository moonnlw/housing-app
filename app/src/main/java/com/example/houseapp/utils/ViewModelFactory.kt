package com.example.houseapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.houseapp.AuthViewModel
import com.example.houseapp.data.repos.AuthRepository
import com.example.houseapp.data.repos.RequestsRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.ui.admin.RequestItemAdminViewModel
import com.example.houseapp.ui.admin.RequestsAdminViewModel
import com.example.houseapp.ui.user.homescreen.ProfileViewModel
import com.example.houseapp.ui.user.listscreen.RequestItemUserViewModel
import com.example.houseapp.ui.user.listscreen.RequestsUserViewModel
import com.example.houseapp.ui.user.loginscreen.LoginViewModel
import com.example.houseapp.ui.user.loginscreen.SignupViewModel
import com.example.houseapp.ui.user.newrequestscreen.CreateRequestViewModel

class ViewModelFactory(
    private val requestsRepository: RequestsRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RequestsUserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RequestsUserViewModel(requestsRepository, authRepository) as T
        }
        if (modelClass.isAssignableFrom(RequestItemUserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RequestItemUserViewModel(requestsRepository, userRepository, authRepository) as T
        }
        if (modelClass.isAssignableFrom(RequestsAdminViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RequestsAdminViewModel(requestsRepository, userRepository) as T
        }
        if (modelClass.isAssignableFrom(RequestItemAdminViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RequestItemAdminViewModel(requestsRepository, userRepository) as T
        }
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(userRepository, authRepository) as T
        }
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignupViewModel(authRepository, userRepository) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authRepository, userRepository) as T
        }
        if (modelClass.isAssignableFrom(CreateRequestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateRequestViewModel(userRepository, requestsRepository, authRepository) as T
        }
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(authRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}