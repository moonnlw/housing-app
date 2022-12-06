package com.example.houseapp.listscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houseapp.data.RequestsRepository
import com.example.houseapp.data.UserRequest
import kotlinx.coroutines.*

class RequestsViewModel(private val requestsRepository: RequestsRepository) : ViewModel() {

    var userId: String = ""
        set(value) {
            field = value
            refreshRequests()
        }

    val requests: LiveData<List<UserRequest>>
        get() = _requests

    private val _requests = requestsRepository.requests

    // TODO check for admin
    private fun refreshRequests() {
        viewModelScope.launch(Dispatchers.Default) {
            requestsRepository.refreshUserRequests(userId)
        }
    }

    /*fun addRequest(userRequest: UserRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            requestsRepository.addRequest(userRequest)
        }
    }*/
}