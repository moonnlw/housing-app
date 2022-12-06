package com.example.houseapp.listscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.houseapp.data.RequestsRepository
import com.example.houseapp.data.UserRequest


class RequestItemViewModel(private val requestsRepository: RequestsRepository) : ViewModel() {

    private var requestLiveData: LiveData<UserRequest>? = null

    fun getRequest(id: Int): LiveData<UserRequest> =
        requestLiveData ?: requestsRepository.getRequest(id).also { requestLiveData = it }
}
