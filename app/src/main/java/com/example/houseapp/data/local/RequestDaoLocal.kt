package com.example.houseapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RequestDaoLocal {

    @Query("SELECT * FROM databaserequest WHERE user_id = :userId")
    fun getUserRequests(userId: String?): Flow<List<DatabaseRequest>>

    @Query("SELECT * FROM databaserequest WHERE req_id = :id")
    fun getRequest(id: Int): Flow<DatabaseRequest>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(requests: List<DatabaseRequest>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(requests: DatabaseRequest)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(request: DatabaseRequest)
}