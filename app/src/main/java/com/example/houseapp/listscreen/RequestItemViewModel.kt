package com.example.houseapp.listscreen

import androidx.lifecycle.*
import com.example.houseapp.data.RequestsRepository
import com.example.houseapp.data.UserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RequestItemViewModel(private val requestsRepository: RequestsRepository) : ViewModel() {

    var requestId: Int = 0
        set(value) {
            field = value
            refreshRequest()
        }

    val request: LiveData<UserRequest>
        get() = _request

    private val _request = MutableLiveData<UserRequest>()

    private fun refreshRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            _request.postValue(requestsRepository.getRequest(requestId))
        }
    }
}
