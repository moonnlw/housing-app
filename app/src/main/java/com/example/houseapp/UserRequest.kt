package com.example.houseapp

class UserRequest( val userId: Int, val problemType: ProblemType, val text: String) {
    val isDone: Boolean = false
}