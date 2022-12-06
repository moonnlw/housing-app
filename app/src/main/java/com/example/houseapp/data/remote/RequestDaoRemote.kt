package com.example.houseapp.data.remote

import com.example.houseapp.data.UserRequest
import com.example.houseapp.utils.DatabaseConnection.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class RequestDaoRemote : RequestDao {
    private fun resultRowToRequest(row: ResultRow) = UserRequest(
        requestId = row[UserRequests.requestId],
        userId = row[UserRequests.userId],
        problemType = row[UserRequests.problemType],
        description = row[UserRequests.description],
        isDone = row[UserRequests.isDone]
    )

    override suspend fun getAllRequests(): List<UserRequest> = dbQuery {
        UserRequests.selectAll().map(::resultRowToRequest)
    }

    override suspend fun getRequest(id: Int): UserRequest? = dbQuery {
        UserRequests
            .select { UserRequests.requestId eq id }
            .map(::resultRowToRequest)
            .singleOrNull()
    }

    override suspend fun getRequestsByUserID(userId: String): List<UserRequest> = dbQuery {
        UserRequests
            .select(UserRequests.userId eq userId)
            .map(::resultRowToRequest)
    }

    override suspend fun deleteRequest(id: Int): Boolean = dbQuery {
        UserRequests.deleteWhere { requestId eq id } > 0
    }

    override suspend fun addNewRequest(userRequest: UserRequest): UserRequest? = dbQuery {
        val insertStatement = UserRequests.insert {
            it[userId] = userRequest.userId
            it[problemType] = userRequest.problemType
            it[description] = userRequest.description
            it[isDone] = userRequest.isDone
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToRequest)
    }
}