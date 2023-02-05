package com.example.houseapp.ui.user.listscreen

import android.util.Log
import androidx.lifecycle.*
import com.example.houseapp.data.models.Response
import com.example.houseapp.data.models.Response.Failure
import com.example.houseapp.data.models.Response.Success
import com.example.houseapp.data.repos.RequestsRepository
import com.example.houseapp.data.models.UserRequest
import com.example.houseapp.ui.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


@OptIn(ExperimentalCoroutinesApi::class)
class RequestListViewModel(
    private val requestsRepository: RequestsRepository
) : BaseViewModel() {

    val userId: MutableStateFlow<String?> = MutableStateFlow(null)

    val requests: LiveData<List<UserRequest>> =
        userId.flatMapLatest {
            requestsRepository.getUserRequests(it)
        }.asLiveData()

    /**
     * emit результата [Response] подгрузки запросов по userId
     */
    private val resultFlow: Flow<Response<Boolean>> =
        userId
            .filterNotNull()
            .mapLatest {
                Log.i(this.javaClass.simpleName, "flow is working, value: $it")
                requestsRepository.refreshUserRequests(it)
            }

    init {
        refreshRequests()
    }

    /**
     * Обработка значений испускаемых resultFlow
     */
    fun refreshRequests() {
        _isLoading.value = true

        viewModelScope.launch {
            resultFlow.collect { result ->
                when (result) {
                    is Success ->
                        Log.i(this.javaClass.simpleName, "Requests refreshed")
                    is Failure -> {
                        Log.e(this.javaClass.simpleName, result.ex.toString())
                        showMessage("Probably, you have bad internet connection")
                    }
                }
                _isLoading.postValue(false)
            }
        }
    }
}
