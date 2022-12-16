package com.example.houseapp.ui.listscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houseapp.data.RequestsRepository
import com.example.houseapp.data.models.UserRequest
import kotlinx.coroutines.*
import java.sql.SQLException

class RequestsViewModel(private val requestsRepository: RequestsRepository) : ViewModel() {

    var userId: String = ""
        set(value) {
            field = value
            refreshRequests()
        }

    val requests: LiveData<List<UserRequest>>
        get() = _requests

    private val _requests = requestsRepository.requests

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isLoading = MutableLiveData(false)

    // TODO check for admin
    fun refreshRequests() {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.Default) {
            try {
                requestsRepository.refreshUserRequests(userId)
            } catch (_: SQLException) {
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}