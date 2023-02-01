package com.example.houseapp.data.models

data class UserWithRequests(
    val user: User,
    val requests: List<UserRequest>
)