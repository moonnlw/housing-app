package com.example.houseapp.ui.admin

import android.util.Log
import androidx.lifecycle.*
import com.example.houseapp.data.models.Response
import com.example.houseapp.data.repos.RequestsRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.data.models.User
import com.example.houseapp.data.models.UserRequest
import com.example.houseapp.ui.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoroutinesApi::class)
class RequestItemAdminViewModel(
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

    /**
     * Валидация запроса, отправка запроса и обработка результата
     */
    fun answerRequest(solution: Boolean) {
        if (request.value?.answer.isNullOrBlank()) {
            showMessage("Please fill the answer field before accepting")
        } else {
            _isLoading.value = true
            viewModelScope.launch {
                val newRequest = request.value!!.copy(isDone = true, solution = solution)
                Log.i(this.javaClass.simpleName, newRequest.toString())
                when (val result = requestsRepository.updateRequest(newRequest)) {
                    is Response.Success -> showMessage("Answer has been sent")
                    is Response.Failure -> {
                        showMessage("Error occurred, check internet connection")
                        Log.e(this.javaClass.simpleName, result.ex.toString())
                    }
                }
                _isLoading.postValue(false)
            }
        }
    }
}