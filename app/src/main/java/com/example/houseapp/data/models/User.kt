package com.example.houseapp.data.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.houseapp.BR

data class User(
    val id: String,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var phone: String = ""
) : BaseObservable() {

    var bFirstName: String
        @Bindable get() = firstName
        set(value) {
            firstName = value
            notifyPropertyChanged(BR.bFirstName)
        }

    var bLastName: String
        @Bindable get() = lastName
        set(value) {
            lastName = value
            notifyPropertyChanged(BR.bLastName)
        }

    var bAddress: String
        @Bindable get() = address
        set(value) {
            address = value
            notifyPropertyChanged(BR.bAddress)
        }

    var bPhone: String
        @Bindable get() = phone
        set(value) {
            phone = value
            notifyPropertyChanged(BR.bPhone)
        }
}

