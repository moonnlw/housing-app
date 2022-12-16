package com.example.houseapp.data


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
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

    fun getUser(id: String): LiveData<User> =
        Transformations.map(database.userDaoLocal.getUser(id)) { it?.asDomainModel() }

    suspend fun refreshUser(id: String) {
        if (database.userDaoLocal.count() == 0) {
            Log.e(javaClass.simpleName, "room is empty")
            val newUser = userDaoRemote.getUserByID(id)
            database.userDaoLocal.insert(newUser.asDatabaseModel())
        }
    }

    suspend fun update(user: User) {
        userDaoRemote.update(user)
        database.userDaoLocal.insert(user.asDatabaseModel())
    }
}