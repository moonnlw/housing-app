package com.example.houseapp.ui.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houseapp.data.repos.RequestsRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.data.models.User
import com.example.houseapp.data.models.UserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLException

class RequestItemAdminViewModel(
    private val requestsRepository: RequestsRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private var _user = MutableLiveData<User>()
    private var _request = MutableLiveData<UserRequest>()
    private val _isLoading = MutableLiveData(false)

    val user: LiveData<User>
        get() = _user
    val request: LiveData<UserRequest>
        get() = _request
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    var requestId: Int = 0
        set(value) {
            field = value
            get()
        }

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
                //requestsRepository.updateRequest(answer, solution, requestId)
            } catch (ex: SQLException) {
                Log.e(this.javaClass.simpleName, "Database error occurred")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}


