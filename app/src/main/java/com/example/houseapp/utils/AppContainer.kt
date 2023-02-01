package com.example.houseapp.utils

import android.content.Context
import com.example.houseapp.data.local.LocalDatabase
import com.example.houseapp.data.remote.RequestDaoRemote
import com.example.houseapp.data.remote.UserDaoRemote
import com.example.houseapp.data.repos.*
import com.google.firebase.auth.FirebaseAuth

class AppContainer(context: Context) {

    private val requestsRepository =
        RequestsRepositoryImpl.getInstance(RequestDaoRemote(), LocalDatabase.getDatabase(context))

    private val userRepository =
        UserRepositoryImpl.getInstance(UserDaoRemote(), LocalDatabase.getDatabase(context))

    private val authRepository =
        AuthRepositoryImpl.getInstance(UserDaoRemote(), FirebaseAuth.getInstance())

    val viewModelFactory = ViewModelFactory(
        requestsRepository,
        userRepository,
        authRepository
    )
}