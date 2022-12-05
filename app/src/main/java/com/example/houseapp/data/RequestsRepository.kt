package com.example.houseapp.data

import androidx.lifecycle.MutableLiveData


class RequestsRepository private constructor(private val requestDao: RequestDao) {

    companion object {
        @Volatile
        private var instance: RequestsRepository? = null

        fun getInstance(requestDao: RequestDao) =
            instance ?: synchronized(this) {
                instance ?: RequestsRepository(requestDao).also { instance = it }
            }
    }

    val requests = MutableLiveData<List<UserRequest>>(emptyList())

//    suspend fun getAllRequests() {
//        requests.postValue(requestDao.getAllRequests())
//    }
//
//    suspend fun getUserRequests(userId: String) = requestDao.getRequestsByUserID(userId)

    suspend fun refreshUserRequests(id: String) {
        val newRequests = requestDao.getRequestsByUserID(id)
        if (newRequests != requests.value) requests.postValue(newRequests)
    }

//    suspend fun refreshRequests() {
//        val newRequests = requestDao.getAllRequests()
//        if (newRequests != requests.value) requests.postValue(newRequests)
//    }
//
//    suspend fun addRequest(userRequest: UserRequest) {
//        requestDao.addNewRequest(userRequest)
//    }

    suspend fun getRequest(id: Int) = requestDao.getRequest(id)
}
