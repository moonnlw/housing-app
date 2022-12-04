package com.example.houseapp.data


class RequestsRepository private constructor(private val requestDao: RequestDao) {

    companion object {
        @Volatile
        private var instance: RequestsRepository? = null

        fun getInstance(requestDao: RequestDao) =
            instance ?: synchronized(this) {
                instance ?: RequestsRepository(requestDao).also { instance = it }
            }
    }

    //val requests = MutableLiveData<List<UserRequest>>(listOf(UserRequest("4","ff","help")))

    //private fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { postValue(initialValue) }

    //val requests = MutableLiveData<List<UserRequest>>().also { it.postValue(emptyList()) }

//    suspend fun getAllRequests() {
//        requests.postValue(requestDao.getAllRequests())
//    }

    suspend fun getAllRequests() = requestDao.getAllRequests()

//    suspend fun refreshUserRequests(id: Int) {
//        val newRequests = requestDao.getRequestsByUserID(id.toString())
//        if (newRequests != requests.value) requests.postValue(newRequests)
//    }
//
//    suspend fun refreshRequests() {
//        withContext(Dispatchers.IO) {
//            val newRequests = requestDao.getAllRequests()
//            if (newRequests != requests.value) requests.postValue(newRequests)
//        }
//    }

    suspend fun addRequest(userRequest: UserRequest) {
        requestDao.addNewRequest(userRequest)
    }

    suspend fun getRequest(id: Int) = requestDao.getRequest(id)

    suspend fun getUserRequests(userId: String) = requestDao.getRequestsByUserID(userId)
}
