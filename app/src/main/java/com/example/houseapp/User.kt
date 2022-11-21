package com.example.houseapp

import java.util.*

class User(val location: String) {
    var phone: String = ""
    val id : Int = 0
    lateinit var requests : List<UserRequest>

    fun send(text: String, problemType: ProblemType) {
        val userRequest = UserRequest(id, text)
    }
}