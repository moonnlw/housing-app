package com.example.houseapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.houseapp.data.RequestsRepository
import com.example.houseapp.ui.listscreen.RequestItemViewModel
import com.example.houseapp.ui.listscreen.RequestsViewModel

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