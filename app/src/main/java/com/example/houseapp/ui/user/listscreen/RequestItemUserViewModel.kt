package com.example.houseapp.ui.user.listscreen

import androidx.lifecycle.*
import com.example.houseapp.data.repos.RequestsRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.data.models.User
import com.example.houseapp.data.models.UserRequest
import com.example.houseapp.data.repos.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*


@OptIn(ExperimentalCoroutinesApi::class)
class RequestItemUserViewModel(
    private val requestsRepository: RequestsRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val userId = authRepository.observeUserId()
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    val requestId = MutableStateFlow(0)

    val user: LiveData<User?> = userId.flatMapLatest { id ->
        userRepository.getUser(id)
    }.asLiveData()

    val request: LiveData<UserRequest> = requestId.flatMapLatest { reqId ->
        requestsRepository.getRequest(reqId)
    }.asLiveData()
}