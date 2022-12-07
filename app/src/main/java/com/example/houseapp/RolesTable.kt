package com.example.houseapp

import com.example.houseapp.UserRequests.autoIncrement
import com.example.houseapp.UserRequests.default
import org.jetbrains.exposed.sql.Table

object Roles : Table() {
    val userId = varchar("userid", 100)
    val isAdmin = bool("is_admin").default(false)
}