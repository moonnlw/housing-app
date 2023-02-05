package com.example.houseapp.data.models

import com.example.houseapp.data.local.DatabaseRequest
import com.example.houseapp.data.local.DatabaseUser


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
        id = id,
        firstName = firstName,
        lastName = lastName,
        address = address,
        phoneNumber = phone
    )
}

fun DatabaseUser.asDomainModel(): User {
    return User(
        id = id,
        firstName = firstName.orEmpty(),
        lastName = lastName.orEmpty(),
        address = address.orEmpty(),
        phone = phoneNumber.orEmpty()
    )
}
