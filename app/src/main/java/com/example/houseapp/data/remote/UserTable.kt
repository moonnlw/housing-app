package com.example.houseapp.data.remote

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val userId = varchar("userid", 100)
    val isAdmin = bool("is_admin").default(false)
    val firstName = varchar("name", 30)
    val lastName = varchar("surname", 30)
    val address = varchar("address", 50)
    val phoneNumber = varchar("phone", 20)
}
