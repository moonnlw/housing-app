package com.example.houseapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RequestDaoLocal {
    @Query("SELECT * FROM databaserequest")
    fun getAllRequests(): LiveData<List<DatabaseRequest>>

    @Query("SELECT * FROM databaserequest WHERE req_id = :id")
    fun getRequest(id: Int): LiveData<DatabaseRequest>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(requests: List<DatabaseRequest>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(requests: DatabaseRequest)

    @Query("UPDATE databaserequest " +
            "SET req_is_done = 1, req_answer =:answer, solution =:solution " +
            "WHERE req_id =:requestId")
    fun update(answer: String, solution: Boolean, requestId: Int)
}