package com.example.houseapp

import org.jetbrains.exposed.sql.Table

object UserRequests : Table() {
    val userId = integer("userid")
    val type = varchar("problemtype", 50).nullable()
    val description = varchar("description", 1000).nullable()
    val requestId = integer("requestid").autoIncrement("seq")
    val isDone = bool("isdone")
}