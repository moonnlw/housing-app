package com.example.houseapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.houseapp.data.UserRequest

@Entity
data class DatabaseRequest constructor(
    @PrimaryKey
    val userId: String,
    val problemType: String,
    val description: String,
    val requestId: Int,
    val isDone: Boolean
)


fun List<DatabaseRequest>.asDomainModel(): List<UserRequest> {
    return map {
        UserRequest(
            requestId = it.requestId,
            userId = it.userId,
            problemType = it.problemType,
            description = it.description,
            isDone = it.isDone
        )
    }
}

fun DatabaseRequest.asDomainModel(): UserRequest {
    return UserRequest(
        requestId = requestId,
        userId = userId,
        problemType = problemType,
        description = description,
        isDone = isDone
    )
}