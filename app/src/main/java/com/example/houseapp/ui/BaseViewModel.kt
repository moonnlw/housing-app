package com.example.houseapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.houseapp.data.models.Event
import com.example.houseapp.data.models.NavigationCommand
import com.example.houseapp.data.models.UIMessage

abstract class BaseViewModel : ViewModel() {
    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    private val _message = MutableLiveData<Event<UIMessage>>()
    protected val _isLoading = MutableLiveData(false)

    val navigation: LiveData<Event<NavigationCommand>> get() =_navigation
    val message: LiveData<Event<UIMessage>> get() = _message
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun navigate(navDirections: NavDirections) {
        _navigation.postValue(Event(NavigationCommand.NavigateToDirection(navDirections)))
    }

    fun navigateBack() {
        _navigation.postValue(Event(NavigationCommand.Back))
    }

    fun showResourceMessage(resId: Int) {
        _message.postValue(Event(UIMessage.StringResource(resId)))
    }

    fun showMessage(message: String) {
        _message.postValue(Event(UIMessage.DynamicString(message)))
    }
}