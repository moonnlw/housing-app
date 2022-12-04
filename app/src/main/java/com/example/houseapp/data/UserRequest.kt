package com.example.houseapp.data

data class UserRequest(
    val requestId: Int? = null,
    val userId: String,
    val problemType: String,
    val description: String,
    val isDone: Boolean = false,
)