package com.example.houseapp.data.local

import androidx.room.*

@Entity
data class DatabaseUser constructor(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "phone_number") val phoneNumber: String?
)

@Entity(foreignKeys = [ForeignKey
    (
        entity = DatabaseUser::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("user_id"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
])
data class DatabaseRequest constructor(
    @PrimaryKey @ColumnInfo(name = "req_id") val requestId: Int,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "problem") val problemType: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "req_is_done") val isDone: Boolean,
    @ColumnInfo(name = "req_answer") val answer: String?,
    @ColumnInfo(name = "solution") val solution: Boolean?
)

data class DatabaseUserWithRequests(
    @Embedded val user: DatabaseUser,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val requests: List<DatabaseRequest>
)