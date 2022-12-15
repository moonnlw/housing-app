package com.houseapp

import android.content.Context
import com.houseapp.data.RequestsRepository
import com.houseapp.data.local.LocalDatabase
import com.houseapp.data.remote.RequestDaoRemote

class AppContainer(context: Context) {

    private val requestsRepository =
        RequestsRepository.getInstance(RequestDaoRemote(), LocalDatabase.getDatabase(context))

    val requestsViewModelFactory = RequestsViewModelFactory(requestsRepository)
}