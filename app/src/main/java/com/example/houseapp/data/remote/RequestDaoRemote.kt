package com.example.houseapp.data.remote

import com.example.houseapp.data.models.UserRequest
import com.example.houseapp.utils.DatabaseConnection.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class RequestDaoRemote {
    private fun resultRowToRequest(row: ResultRow) = UserRequest(
        requestId = row[UserRequests.requestId],
        userId = row[UserRequests.userId],
        problemType = row[UserRequests.problemType],
        description = row[UserRequests.description],
        isDone = row[UserRequests.isDone]
    )

    suspend fun getAllRequests(): List<UserRequest> = dbQuery {
        UserRequests.selectAll().map(::resultRowToRequest)
    }

    suspend fun getRequestsByUserID(userId: String): List<UserRequest> = dbQuery {
        UserRequests
            .select(UserRequests.userId eq userId)
            .map(::resultRowToRequest)
    }

    suspend fun addNewRequest(userRequest: UserRequest): UserRequest? = dbQuery {
        val insertStatement = UserRequests.insert {
            it[userId] = userRequest.userId
            it[problemType] = userRequest.problemType
            it[description] = userRequest.description
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToRequest)
    }
}