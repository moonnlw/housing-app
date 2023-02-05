package com.example.houseapp.ui.user.newrequestscreen

import android.util.Log
import androidx.lifecycle.*
import com.example.houseapp.data.repos.RequestsRepository
import com.example.houseapp.data.repos.UserRepository
import com.example.houseapp.data.models.Response
import com.example.houseapp.data.models.User
import com.example.houseapp.data.models.UserRequest
import com.example.houseapp.ui.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class CreateRequestViewModel(
    private val userRepository: UserRepository,
    private val requestsRepository: RequestsRepository
) : BaseViewModel() {

    val userId: MutableStateFlow<String?> = MutableStateFlow("")

    /**
     * Поля для 2-way data-binding.
     */
    val problemType = MutableLiveData("")
    val problemDescription = MutableLiveData("")

    private val user = userId.flatMapLatest { id ->
        userRepository.getUser(id)
    }

    /**
     * Создание заявки, валидация и обработка результата.
     */
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
                        showMessage("Your request has been sent successfully")
                        problemDescription.postValue("")
                        problemType.postValue("")
                    }
                    is Response.Failure -> {
                        showMessage("Something went wrong. Please, check your internet connection or try again later")
                        Log.e(this.javaClass.simpleName, result.ex.message.toString())
                    }
                }
            }
            _isLoading.postValue(false)
        }
    }

    /**
     * Проверка заявки на валидность.
     */
    private fun isDataValid(user: User?): Boolean {
        Log.i(this.javaClass.simpleName, "uid: ${user.toString()}")

        return when {
            !isEnoughUserData(user) -> {
                showMessage("Please, fill personal data before creating request")
                navigate(CreateRequestFragmentDirections.actionNavigateHome())
                false
            }
            problemType.value.isNullOrBlank() || problemDescription.value.isNullOrBlank() -> {
                showMessage("Please fill all fields before send a request")
                false
            }
            problemDescription.value!!.length > MAX_LENGTH_REQUEST -> {
                showMessage("Too large message")
                false
            }
            else -> true
        }
    }

    /**
     * Проверяет, заполнены ли данные о пользователе.
     */
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