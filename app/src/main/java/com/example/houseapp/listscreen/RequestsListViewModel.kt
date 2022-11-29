package com.example.houseapp.listscreen

import androidx.lifecycle.ViewModel

class RequestsListViewModel : ViewModel() {
    private var userId: String = ""

    fun setUserId(id: String) {
        userId = id;
    }

    fun getUserId(): String {
        return userId
    }
}