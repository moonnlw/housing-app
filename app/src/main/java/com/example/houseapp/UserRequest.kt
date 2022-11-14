package com.example.houseapp

import com.example.houseapp.ProblemType
import java.util.*

class UserRequest
    (
    val userId: UUID,
    val problemType: ProblemType,
    val text: String
) {
    val requestId: UUID = UUID.randomUUID()
    val isDone: Boolean = false
}