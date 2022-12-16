package com.example.houseapp.data

import com.example.houseapp.data.local.DatabaseRequest
import com.example.houseapp.data.local.DatabaseUser
import com.example.houseapp.data.models.User
import com.example.houseapp.data.models.UserRequest


fun DatabaseUser.asDomainModel(): User {
    return User(
        userId = userId,
        firstName = firstName,
        lastName = lastName,
        address = address,
        phone = phoneNumber
    )
}

fun List<DatabaseRequest>.asDomainModel(): List<UserRequest> {
    return map { it.asDomainModel() }
}

fun DatabaseRequest.asDomainModel(): UserRequest {
    return UserRequest(
        requestId = requestId,
        userId = userId,
        problemType = problemType,
        description = description,
        isDone = isDone,
        answer = answer,
        solution = solution
    )
}

fun List<UserRequest>.asDatabaseModel(): List<DatabaseRequest> {
    return map { it.asDatabaseModel() }
}

fun UserRequest.asDatabaseModel(): DatabaseRequest {
    return DatabaseRequest(
        requestId = requestId!!,
        userId = userId,
        problemType = problemType,
        description = description,
        isDone = isDone,
        answer = answer,
        solution = solution
    )
}

fun User.asDatabaseModel(): DatabaseUser {
    return DatabaseUser(
        userId = userId,
        firstName = firstName!!,
        lastName = lastName!!,
        address = address!!,
        phoneNumber = phone!!
    )
}
