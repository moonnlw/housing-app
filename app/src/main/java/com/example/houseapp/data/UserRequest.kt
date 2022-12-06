package com.example.houseapp.data

import com.example.houseapp.data.local.DatabaseRequest

class UserRequest(
    val requestId: Int? = null,
    val userId: String,
    val problemType: String,
    val description: String,
    val isDone: Boolean = false,
)

fun List<UserRequest>.asDatabaseModel(): List<DatabaseRequest> {
    return map {
        DatabaseRequest(
            requestId = it.requestId!!,
            userId = it.userId,
            problemType = it.problemType,
            description = it.description,
            isDone = it.isDone
        )
    }
}