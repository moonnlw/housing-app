package com.example.houseapp.listscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.houseapp.data.RequestsRepository
import com.example.houseapp.data.UserRequest


class RequestItemViewModel(private val requestsRepository: RequestsRepository) : ViewModel() {

    private var requestLiveData: LiveData<UserRequest>? = null

    fun getRequest(id: Int): LiveData<UserRequest> {
        return requestLiveData ?: liveData {
            requestsRepository.getRequest(id)?.let { emit(it) }
        }.also { requestLiveData = it }
    }

    /*private fun updateRequest(userRequest: UserRequest) {

    }*/
}
