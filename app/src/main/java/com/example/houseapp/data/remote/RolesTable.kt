package com.example.houseapp

import org.jetbrains.exposed.sql.Table

object Roles : Table() {
    val userId = varchar("userid", 100)
    val isAdmin = bool("is_admin").default(false)
}