package com.example.houseapp.data.remote

import com.example.houseapp.data.models.User
import com.example.houseapp.utils.DatabaseConnection.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

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

    suspend fun getUserByID(id: String): User = dbQuery {
        Users
            .select(Users.userId eq id)
            .map(::resultRowToUser)
            .first()
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

    suspend fun update(user: User) = dbQuery {
        Users.update({Users.userId eq user.userId}) {
            it[firstName] = user.firstName!!
            it[lastName] = user.lastName!!
            it[address] = user.address!!
            it[phoneNumber] = user.phone!!
        }
    }
}
