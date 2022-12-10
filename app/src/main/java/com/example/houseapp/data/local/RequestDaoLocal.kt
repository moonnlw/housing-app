package com.example.houseapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RequestDaoLocal {
    @Query("SELECT * FROM databaserequest")
    fun getAllRequests():LiveData<List<DatabaseRequest>>

    @Query("SELECT * FROM databaserequest WHERE requestId = :id")
    fun getRequest(id: Int): DatabaseRequest

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(requests: List<DatabaseRequest>)
}