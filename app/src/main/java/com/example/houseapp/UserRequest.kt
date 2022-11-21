package com.example.houseapp

import com.example.houseapp.ProblemType
import java.util.*

class UserRequest
    (
    val userId: Int,
    //val problemType: ProblemType,
    val text: String
) {
    val requestId: UUID = UUID.randomUUID()
    val isDone: Boolean = false
}