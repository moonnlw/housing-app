package com.example.houseapp

import com.example.houseapp.data.RequestDaoImpl
import com.example.houseapp.data.RequestsRepository

object InjectorUtils {
    fun provideRequestsViewModelFactory(): RequestsViewModelFactory {
        val requestsRepository =
            RequestsRepository.getInstance(RequestDaoImpl())
        return RequestsViewModelFactory(requestsRepository)
    }
}