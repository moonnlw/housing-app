package com.example.houseapp.data


import com.example.houseapp.data.local.LocalDatabase
import com.example.houseapp.data.models.User
import com.example.houseapp.data.remote.UserDaoRemote

class UserRepository private constructor(
    private val userDaoRemote: UserDaoRemote,
    private val database: LocalDatabase
) {

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(userDaoRemote: UserDaoRemote, database: LocalDatabase) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userDaoRemote, database).also { instance = it }
            }
    }

    suspend fun getUser(id: String): User =
        when (database.userDaoLocal.count()) {
            0 -> userDaoRemote.getUserByID(id)
            else -> database.userDaoLocal.getUser(id).asDomainModel()
        }
}