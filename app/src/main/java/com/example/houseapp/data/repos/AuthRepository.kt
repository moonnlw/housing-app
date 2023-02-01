package com.example.houseapp.data.repos

import com.example.houseapp.data.models.Response
import com.example.houseapp.data.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getUserId(): String?
    fun observeUserId(): Flow<String?>
    fun signOut(): Response<Boolean>
    suspend fun signIn(email: String, password: String): Response<User>
    suspend fun createUser(email: String, password: String): Response<User>
    suspend fun deleteUser(): Response<Boolean>
}