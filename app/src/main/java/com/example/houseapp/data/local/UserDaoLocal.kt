package com.example.houseapp.data.local


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDaoLocal {
    @Query("SELECT * FROM databaseuser WHERE id = :id")
    fun getUser(id: String?): Flow<DatabaseUser?>

    @Query("SELECT * FROM databaseuser")
    fun getAllUsers(): Flow<List<DatabaseUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: DatabaseUser)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(user: DatabaseUser)

    @Query("SELECT COUNT(*) FROM databaseuser")
    suspend fun count(): Int
}