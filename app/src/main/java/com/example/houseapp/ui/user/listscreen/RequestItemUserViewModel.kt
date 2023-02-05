package com.example.houseapp.ui.user.listscreen

import androidx.lifecycle.*
import com.example.houseapp.data.repos.RequestsRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.data.models.User
import com.example.houseapp.data.models.UserRequest
import com.example.houseapp.ui.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*


@OptIn(ExperimentalCoroutinesApi::class)
class RequestItemUserViewModel(
    private val requestsRepository: RequestsRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val userId = MutableStateFlow("")
    val requestId = MutableStateFlow(0)

    val user: LiveData<User?> = userId.flatMapLatest { id ->
        userRepository.getUser(id)
    }.asLiveData()

    val request: LiveData<UserRequest> = requestId.flatMapLatest { reqId ->
        requestsRepository.getRequest(reqId)
    }.asLiveData()
}