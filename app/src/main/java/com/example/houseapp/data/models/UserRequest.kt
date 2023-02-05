package com.example.houseapp.data.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.houseapp.BR

data class UserRequest(
    val requestId: Int? = null,
    val userId: String,
    val problemType: String,
    val description: String,
    val isDone: Boolean = false,
    var answer: String = "",
    var solution: Boolean = false
) : BaseObservable() {

    var bAnswer: String
        @Bindable get() = answer
        set(value) {
            answer = value
            notifyPropertyChanged(BR.bAnswer)
        }
}
