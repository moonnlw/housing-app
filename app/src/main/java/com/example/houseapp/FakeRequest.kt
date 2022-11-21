package com.example.houseapp

data class FakeRequest(
    val problemType: String = "problemType",
    val text: String = "heeeeeeeeeeeeeeeeeeeeeeeeeelp",
    val user_id: Int = 1,
    val id: Int,
    val isDone: Boolean = true
)