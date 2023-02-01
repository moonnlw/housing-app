package com.example.houseapp.utils

import com.example.houseapp.data.local.DatabaseUser

object TestUtil {
    fun createDatabaseUser(id: String) = DatabaseUser(
        id = id,
        firstName = null,
        lastName = null,
        address = null,
        phoneNumber = null
    )
}
