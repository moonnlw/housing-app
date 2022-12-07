package com.example.houseapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.houseapp.data.local.LocalDatabase
import com.example.houseapp.data.local.asDomainModel
import com.example.houseapp.data.remote.RequestDao
import com.example.houseapp.utils.NetworkConnection


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
        Transformations.map(database.requestDaoLocal.getRequest(id)) {
            it.asDomainModel()
        }

/*    suspend fun refreshUserRequests(id: String) {
        val newRequests = requestDao.getRequestsByUserID(id)
        if (newRequests != requests.value) requests.postValue(newRequests)
    }*/

    suspend fun refreshUserRequests(id: String) {
        if (NetworkConnection.isNetworkAvailable) {
            val newRequests = requestDao.getRequestsByUserID(id)
            database.requestDaoLocal.insertAll(newRequests.asDatabaseModel())
        }
    }

/*    suspend fun refreshRequests() {
        val newRequests = requestDao.getAllRequests()
        database.requestDaoLocal.insertAll(newRequests.asDatabaseModel())
    }*/

//    val requests = MutableLiveData<List<UserRequest>>(emptyList())

//    suspend fun getAllRequests() {
//        requests.postValue(requestDao.getAllRequests())
//    }
//
//    suspend fun getUserRequests(userId: String) = requestDao.getRequestsByUserID(userId)

//    suspend fun refreshRequests() {
//        val newRequests = requestDao.getAllRequests()
//        if (newRequests != requests.value) requests.postValue(newRequests)
//    }
//
   suspend fun addRequest(userRequest: UserRequest) {
        requestDao.addNewRequest(userRequest)
    }
}
