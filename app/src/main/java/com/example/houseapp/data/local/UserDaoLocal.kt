package com.example.houseapp.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDaoLocal {
    @Query("SELECT * FROM databaseuser WHERE user_id = :id")
    fun getUser(id: String): DatabaseUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: DatabaseUser)

    @Query("SELECT COUNT(*) FROM databaseuser")
    fun count(): Int
}