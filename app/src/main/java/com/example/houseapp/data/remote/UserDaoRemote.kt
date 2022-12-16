package com.example.houseapp.data.remote

import android.util.Log
import com.example.houseapp.data.models.User
import com.example.houseapp.utils.DatabaseConnection.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class UserDaoRemote {
    private fun resultRowToUser(row: ResultRow) = User(
        userId = row[Users.userId],
        firstName = row[Users.firstName],
        lastName = row[Users.lastName],
        address = row[Users.address],
        phone = row[Users.phoneNumber]
    )

    suspend fun getAllUsers(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }

    suspend fun getUserByID(id: String): User {
        try {
            dbQuery {
                return@dbQuery Users
                    .select(Users.userId eq id)
                    .map(::resultRowToUser)
                    .first()
            }
        } catch (ex: Exception) {
            Log.e(javaClass.simpleName, ex.message.toString())
        } finally {
            return User("", "", "", "", "")
        }
    }

    suspend fun addNewUser(user: User): User? = dbQuery {
        val insertStatement = Users.insert {
            it[userId] = user.userId
            it[firstName] = user.firstName.toString()
            it[lastName] = user.lastName.toString()
            it[address] = user.address.toString()
            it[phoneNumber] = user.phone.toString()
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }
}
