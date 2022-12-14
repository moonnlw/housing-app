package com.example.houseapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.houseapp.data.UserRequest

@Entity
data class DatabaseRequest constructor(
    val userId: String,
    val problemType: String,
    val description: String,
    @PrimaryKey @ColumnInfo(name = "req_id") val requestId: Int,
    @ColumnInfo(name = "req_is_done") val isDone: Boolean,
    @ColumnInfo(name = "req_answer") val answer: String?,
    @ColumnInfo(name = "solution") val solution: Boolean?
)


fun List<DatabaseRequest>.asDomainModel(): List<UserRequest> {
    return map { it.asDomainModel() }
}

fun DatabaseRequest.asDomainModel(): UserRequest {
    return UserRequest(
        requestId = requestId,
        userId = userId,
        problemType = problemType,
        description = description,
        isDone = isDone,
        answer = answer,
        solution = solution
    )
}