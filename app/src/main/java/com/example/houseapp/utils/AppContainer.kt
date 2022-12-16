package com.example.houseapp.utils

import android.content.Context
import com.example.houseapp.data.RequestsRepository
import com.example.houseapp.data.UserRepository
import com.example.houseapp.data.local.LocalDatabase
import com.example.houseapp.data.remote.RequestDaoRemote
import com.example.houseapp.data.remote.UserDaoRemote

class AppContainer(context: Context) {

    private val requestsRepository =
        RequestsRepository.getInstance(RequestDaoRemote(), LocalDatabase.getDatabase(context))

    private val userRepository =
        UserRepository.getInstance(UserDaoRemote(), LocalDatabase.getDatabase(context))

    val requestsViewModelFactory = RequestsViewModelFactory(requestsRepository)
    val userViewModelFactory = UserViewModelFactory(userRepository)
}