package com.houseapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.houseapp.data.local.LocalDatabase
import com.houseapp.data.local.asDomainModel
import com.houseapp.data.remote.RequestDao
import com.houseapp.utils.NetworkConnection


class RequestsRepository private constructor(
    private val requestDao: RequestDao,
    private val database: LocalDatabase
) {

    companion object {
        @Volatile
        private var instance: RequestsRepository? = null

        fun getInstance(requestDao: RequestDao, database: LocalDatabase) =
            instance ?: synchronized(this) {
                instance ?: RequestsRepository(requestDao, database).also { instance = it }
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
            val newRequests = requestDao.getRequestsByUserID(id)
            database.requestDaoLocal.insertAll(newRequests.asDatabaseModel())
        }
    }

    fun updateRequest(answer: String, solution: Boolean, requestId: Int) {
        if (NetworkConnection.isNetworkAvailable) {
            database.requestDaoLocal.update(answer, solution, requestId)
        }
    }
}
