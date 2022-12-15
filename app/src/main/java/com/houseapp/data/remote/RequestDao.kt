package com.houseapp.data.remote

import com.houseapp.data.UserRequest


interface RequestDao {
    suspend fun getAllRequests(): List<UserRequest>
    suspend fun getRequest(id: Int): UserRequest?
    suspend fun getRequestsByUserID(userId: String): List<UserRequest>
    suspend fun deleteRequest(id: Int): Boolean
    suspend fun addNewRequest(userRequest: UserRequest): UserRequest?
}
