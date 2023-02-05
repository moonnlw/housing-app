package com.example.houseapp.data.repos


import android.util.Log
import com.example.houseapp.data.models.asDatabaseModel
import com.example.houseapp.data.models.asDomainModel
import com.example.houseapp.data.local.LocalDatabase
import com.example.houseapp.data.models.Response
import com.example.houseapp.data.models.User
import com.example.houseapp.data.remote.UserDaoRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl private constructor(
    private val userDaoRemote: UserDaoRemote,
    private val database: LocalDatabase
) : UserRepository {

    companion object {
        @Volatile
        private var instance: UserRepositoryImpl? = null

        fun getInstance(userDaoRemote: UserDaoRemote, database: LocalDatabase) =
            instance ?: synchronized(this) {
                instance ?: UserRepositoryImpl(userDaoRemote, database)
                    .also { instance = it }
            }
    }

    override fun getUser(id: String?): Flow<User?> =
        database.userDaoLocal.getUser(id).map { it?.asDomainModel() }

    override fun getAllUsers(): Flow<List<User>> =
        database.userDaoLocal.getAllUsers()
            .map { it.map { user -> user.asDomainModel() } }

    override suspend fun refreshUser(id: String): Response<Boolean> =
        Response.to {
            userDaoRemote.getUserByID(id).also {
                database.userDaoLocal.insert(it.asDatabaseModel())
            }
            true
        }

    override suspend fun refreshAllUsers(): Response<Boolean> =
        Response.to {
            userDaoRemote.getAllUsers().also {
                database.userDaoLocal.insertAll(it.map { user -> user.asDatabaseModel() })
            }
            true
        }


    override suspend fun updateUser(user: User): Response<Boolean> =
        Response.to {
            Log.i(this.javaClass.simpleName, user.toString())
            userDaoRemote.update(user)
            database.userDaoLocal.update(user.asDatabaseModel())
            true
        }

    override suspend fun addUser(user: User): Response<Boolean> =
        Response.to {
            userDaoRemote.addNewUser(user)
            database.userDaoLocal.insert(user.asDatabaseModel())
            true
        }

    override suspend fun isUserAdmin(id: String): Boolean =
        userDaoRemote.getUserIfAdmin(id) != null

    override suspend fun isUserDatabaseEmpty(): Boolean = database.userDaoLocal.count() == 0
}