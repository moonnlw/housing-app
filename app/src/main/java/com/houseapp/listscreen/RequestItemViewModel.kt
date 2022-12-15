package com.houseapp.listscreen

import android.util.Log
import androidx.lifecycle.*
import com.houseapp.data.RequestsRepository
import com.houseapp.data.UserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLException


class RequestItemViewModel(private val requestsRepository: RequestsRepository) : ViewModel() {

    var requestId: Int = 0
        set(value) {
            field = value
            get()
        }

    val request: LiveData<UserRequest>
        get() = _request

    private var _request = MutableLiveData<UserRequest>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isLoading = MutableLiveData(false)

    private fun get() {
        viewModelScope.launch(Dispatchers.IO) {
            _request = requestsRepository.getRequest(requestId) as MutableLiveData<UserRequest>
        }
    }

    fun updateRequest(answer: String, solution: Boolean) {
        _isLoading.value = true
        val requestToUpdate = request.value
        viewModelScope.launch(Dispatchers.IO) {
            try {
                requestsRepository.updateRequest(answer, solution, requestId)
            } catch (ex: SQLException) {
                Log.e(this.javaClass.simpleName, "Database error occurred")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
