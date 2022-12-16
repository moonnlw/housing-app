package com.example.houseapp.data.models

data class UserRequest(
    val requestId: Int? = null,
    val userId: String,
    val problemType: String,
    val description: String,
    val isDone: Boolean = false,
    var answer: String? = null,
    var solution: Boolean? = null
)
