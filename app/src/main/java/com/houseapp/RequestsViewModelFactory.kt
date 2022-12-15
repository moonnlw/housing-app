package com.houseapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.houseapp.data.RequestsRepository
import com.houseapp.listscreen.RequestItemViewModel
import com.houseapp.listscreen.RequestsViewModel

class RequestsViewModelFactory(private val requestsRepository: RequestsRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RequestsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RequestsViewModel(requestsRepository) as T
        }
        if (modelClass.isAssignableFrom(RequestItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RequestItemViewModel(requestsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}