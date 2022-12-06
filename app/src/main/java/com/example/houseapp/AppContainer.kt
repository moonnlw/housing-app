package com.example.houseapp

import android.content.Context
import com.example.houseapp.data.RequestsRepository
import com.example.houseapp.data.local.LocalDatabase
import com.example.houseapp.data.remote.RequestDaoRemote

class AppContainer(context: Context) {

    private val requestsRepository =
        RequestsRepository.getInstance(RequestDaoRemote(), LocalDatabase.getDatabase(context))

    val requestsViewModelFactory = RequestsViewModelFactory(requestsRepository)
}