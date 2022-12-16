package com.example.houseapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.houseapp.data.local.LocalDatabase
import com.example.houseapp.data.models.UserRequest
import com.example.houseapp.data.remote.RequestDaoRemote
import com.example.houseapp.utils.NetworkConnection


class RequestsRepository private constructor(
    private val requestDaoRemote: RequestDaoRemote,
    private val database: LocalDatabase
) {

    companion object {
        @Volatile
        private var instance: RequestsRepository? = null

        fun getInstance(requestDaoRemote: RequestDaoRemote, database: LocalDatabase) =
            instance ?: synchronized(this) {
                instance ?: RequestsRepository(requestDaoRemote, database).also { instance = it }
            }
    }

    val requests: LiveData<List<UserRequest>> =
        Transformations.map(database.requestDaoLocal.getAllRequests()) {
            it.asDomainModel()
        }

    fun getRequest(id: Int): LiveData<UserRequest> =
        Transformations.map(database.requestDaoLocal.getRequest(id)) { it.asDomainModel() }

    suspend fun refreshUserRequests(id: String) {
        if (NetworkConnection.isNetworkAvailable) {
            val newRequests = requestDaoRemote.getRequestsByUserID(id)
            database.requestDaoLocal.insertAll(newRequests.asDatabaseModel())
        }
    }

    fun updateRequest(answer: String, solution: Boolean, requestId: Int) {
        if (NetworkConnection.isNetworkAvailable) {
            database.requestDaoLocal.update(answer, solution, requestId)
        }
    }
}
