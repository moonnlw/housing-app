package com.example.houseapp.ui.user.newrequestscreen

import android.util.Log
import androidx.lifecycle.*
import com.example.houseapp.data.repos.AuthRepository
import com.example.houseapp.data.repos.RequestsRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.data.models.Event
import com.example.houseapp.data.models.Response
import com.example.houseapp.data.models.User
import com.example.houseapp.data.models.UserRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class CreateRequestViewModel(
    private val userRepository: UserRepository,
    private val requestsRepository: RequestsRepository,
    authRepository: AuthRepository
) : ViewModel() {

    private val userId: Flow<String?> = authRepository.observeUserId()
    private val statusMessage = MutableLiveData<Event<String>>()
    private val _isLoading = MutableLiveData(false)
    private val _isEnoughData = MutableLiveData<Event<Boolean>>()

    val isLoading: LiveData<Boolean> get() = _isLoading
    val message: LiveData<Event<String>> get() = statusMessage
    val isEnoughData: LiveData<Event<Boolean>> get() = _isEnoughData

    val problemType = MutableLiveData("")
    val problemDescription = MutableLiveData("")

    private val user = userId.flatMapLatest { id ->
        userRepository.getUser(id)
    }

    fun createRequest() {
        _isLoading.value = true
        viewModelScope.launch {
            val newUserInfo = user.firstOrNull()

            if (isDataValid(newUserInfo)) {
                val request = UserRequest(
                    userId = newUserInfo!!.id,
                    problemType = problemType.value!!,
                    description = problemDescription.value!!
                )
                when (val result = requestsRepository.addNewRequest(request)) {
                    is Response.Success -> {
                        statusMessage.postValue(Event("Your request has been sent successfully"))
                        problemDescription.postValue("")
                        problemType.postValue("")
                    }
                    is Response.Failure -> {
                        statusMessage.postValue(
                            Event("Something went wrong. Please, check your internet connection or try again later")
                        )
                        Log.e(this.javaClass.simpleName, result.ex.message.toString())
                    }
                }
            }
            _isLoading.postValue(false)
        }
    }

    private fun isDataValid(user: User?): Boolean {
        Log.i(this.javaClass.simpleName, "uid: ${user.toString()}")

        return when {
            !isEnoughUserData(user) -> {
                statusMessage.postValue(Event("Please, fill personal data before creating request"))
                _isEnoughData.postValue(Event(false))
                false
            }
            problemType.value.isNullOrBlank() || problemDescription.value.isNullOrBlank() -> {
                statusMessage.postValue(Event("Please fill all fields before send a request"))
                false
            }
            problemDescription.value!!.length > MAX_LENGTH_REQUEST -> {
                statusMessage.postValue(Event("Too large message"))
                false
            }
            else -> true
        }
    }


    private fun isEnoughUserData(user: User?): Boolean {
        Log.i(this.javaClass.simpleName, user.toString())
        return user != null &&
                user.firstName.isNotBlank()
                && user.lastName.isNotBlank()
                && user.address.isNotBlank()
                && user.phone.isNotBlank()
    }

    companion object {
        const val MAX_LENGTH_REQUEST = 300
    }
}