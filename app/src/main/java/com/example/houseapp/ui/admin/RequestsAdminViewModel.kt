package com.example.houseapp.ui.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houseapp.data.repos.RequestsRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.data.models.UserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLException

class RequestsAdminViewModel(
    private val requestsRepository: RequestsRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _requests = MutableLiveData<List<UserRequest>>()
    private val _isLoading = MutableLiveData(false)

    val requests: LiveData<List<UserRequest>>
        get() = _requests
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun refreshRequests() {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                requestsRepository.refreshAllRequests()
            } catch (ex: SQLException) {
                Log.e(this.javaClass.simpleName, ex.message.toString())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}