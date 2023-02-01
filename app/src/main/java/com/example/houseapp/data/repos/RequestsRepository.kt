package com.example.houseapp.data.repos

import com.example.houseapp.data.models.Response
import com.example.houseapp.data.models.UserRequest
import kotlinx.coroutines.flow.Flow

interface RequestsRepository {
    fun getRequest(id: Int): Flow<UserRequest>
    fun getUserRequests(userId: String?): Flow<List<UserRequest>>
    suspend fun refreshUserRequests(id: String?): Response<Boolean>
    suspend fun updateRequest(request: UserRequest): Response<Boolean>
    suspend fun refreshAllRequests(): Response<Boolean>
    suspend fun addNewRequest(request: UserRequest): Response<Boolean>
}