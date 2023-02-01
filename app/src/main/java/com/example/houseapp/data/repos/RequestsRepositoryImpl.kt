package com.example.houseapp.data.repos

import com.example.houseapp.data.models.asDatabaseModel
import com.example.houseapp.data.models.asDomainModel
import com.example.houseapp.data.local.LocalDatabase
import com.example.houseapp.data.models.Response
import com.example.houseapp.data.models.UserRequest
import com.example.houseapp.data.remote.RequestDaoRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class RequestsRepositoryImpl private constructor(
    private val requestDaoRemote: RequestDaoRemote,
    private val database: LocalDatabase
) : RequestsRepository {

    companion object {
        @Volatile
        private var instance: RequestsRepository? = null

        fun getInstance(requestDaoRemote: RequestDaoRemote, database: LocalDatabase) =
            instance ?: synchronized(this) {
                instance ?: RequestsRepositoryImpl(requestDaoRemote, database)
                    .also {
                        instance = it
                    }
            }
    }

    override fun getRequest(id: Int): Flow<UserRequest> =
        database.requestDaoLocal.getRequest(id).map { it.asDomainModel() }

    override fun getUserRequests(userId: String?): Flow<List<UserRequest>> =
        database.requestDaoLocal.getUserRequests(userId)
            .map {
                it.map { user -> user.asDomainModel() }
            }

    override suspend fun refreshUserRequests(id: String?): Response<Boolean> =
        Response.to {
            id?.let {
                val newRequests = requestDaoRemote.getRequestsByUserID(it)
                database.requestDaoLocal.insertAll(newRequests.asDatabaseModel())
            }
            true
        }

    override suspend fun updateRequest(request: UserRequest): Response<Boolean> =
        Response.to {
            database.requestDaoLocal.update(request.asDatabaseModel())
            true
        }

    override suspend fun refreshAllRequests(): Response<Boolean> =
        Response.to {
            val newRequests = requestDaoRemote.getAllRequests()
            database.requestDaoLocal.insertAll(newRequests.asDatabaseModel())
            true
        }

    override suspend fun addNewRequest(request: UserRequest): Response<Boolean> =
        Response.to {
            requestDaoRemote.addNewRequest(request)
            true
        }
}
