package com.example.houseapp

import java.util.*

class User(val location: String) {
    var phone: String = ""
    val id : Int = 0
    lateinit var requests : List<UserRequest>
}