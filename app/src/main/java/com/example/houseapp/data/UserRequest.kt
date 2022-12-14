package com.example.houseapp.data

import com.example.houseapp.data.local.DatabaseRequest

class UserRequest(
    val requestId: Int? = null,
    val userId: String,
    val problemType: String,
    val description: String,
    val isDone: Boolean = false,
    var answer: String? = null,
    var solution: Boolean? = null
)

fun List<UserRequest>.asDatabaseModel(): List<DatabaseRequest> {
    return map { it.asDatabaseModel() }
}

fun UserRequest.asDatabaseModel(): DatabaseRequest {
    return DatabaseRequest(
        requestId = requestId!!,
        userId = userId,
        problemType = problemType,
        description = description,
        isDone = isDone,
        answer = answer,
        solution = solution
    )
}