package com.example.houseapp.ui.user.listscreen

import android.util.Log
import androidx.lifecycle.*
import com.example.houseapp.data.models.Event
import com.example.houseapp.data.models.Response.Failure
import com.example.houseapp.data.models.Response.Success
import com.example.houseapp.data.repos.AuthRepository
import com.example.houseapp.data.repos.RequestsRepository
import com.example.houseapp.data.models.UserRequest
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class RequestsUserViewModel(
    private val requestsRepository: RequestsRepository,
    authRepository: AuthRepository
) : ViewModel() {

    private val userId: Flow<String?> = authRepository.observeUserId()
    private val statusMessage = MutableLiveData<Event<String>>()
    private val _isLoading = MutableLiveData(false)

    val requests: LiveData<List<UserRequest>> = userId.flatMapLatest {
        requestsRepository.getUserRequests(it)
    }.asLiveData()

    val message: LiveData<Event<String>> get() = statusMessage
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        refreshRequests()
    }

    fun refreshRequests() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = userId.mapLatest {
                requestsRepository.refreshUserRequests(it)
            }.first()
            when (result) {
                is Success ->
                    Log.i(this.javaClass.simpleName, "Requests refreshed")
                is Failure -> {
                    Log.e(this.javaClass.simpleName, result.ex.message.toString())
                    statusMessage.postValue(Event("Probably, you have bad internet connection"))
                }
            }
            _isLoading.postValue(false)
        }
    }
}
