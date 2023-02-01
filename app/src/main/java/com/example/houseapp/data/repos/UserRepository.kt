package com.example.houseapp.data.repos

import com.example.houseapp.data.models.Response
import com.example.houseapp.data.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(id: String?): Flow<User?>
    suspend fun getAllUsers(): Flow<List<User>>
    suspend fun isUserDatabaseEmpty(): Boolean
    suspend fun refreshUser(id: String): Response<Boolean>
    suspend fun updateUser(user: User): Response<Boolean>
    suspend fun addUser(user: User): Response<Boolean>
    suspend fun isUserAdmin(id: String): Boolean
}