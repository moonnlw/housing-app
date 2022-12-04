package com.example.houseapp.listscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houseapp.data.RequestsRepository
import com.example.houseapp.data.UserRequest
import kotlinx.coroutines.*

class RequestsViewModel(private val requestsRepository: RequestsRepository) : ViewModel() {

    private var userId: String = ""

    val requests: MutableLiveData<List<UserRequest>> by lazy {
        MutableLiveData<List<UserRequest>>()
    }

    init {
        refreshRequests()
    }

    private fun refreshRequests() {
        viewModelScope.launch(Dispatchers.IO) {
            requests.postValue(requestsRepository.getAllRequests())
        }
    }

    fun addRequest(userRequest: UserRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            requestsRepository.addRequest(userRequest)
        }
    }

    fun setUserId (id: String) {
        userId = id
    }

    fun getUserId(): String {
        return userId
    }
}