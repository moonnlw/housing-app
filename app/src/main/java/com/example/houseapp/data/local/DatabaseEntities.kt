package com.example.houseapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseRequest constructor(
    val userId: String,
    val problemType: String,
    val description: String,
    @PrimaryKey @ColumnInfo(name = "req_id") val requestId: Int,
    @ColumnInfo(name = "req_is_done") val isDone: Boolean,
    @ColumnInfo(name = "req_answer") val answer: String?,
    @ColumnInfo(name = "solution") val solution: Boolean?
)

@Entity
data class DatabaseUser constructor(
    @PrimaryKey @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String
)
