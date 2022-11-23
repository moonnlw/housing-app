package com.example.houseapp

import org.jetbrains.exposed.sql.Table

object UserRequests : Table() {
    val userId = integer("userid")
    val problemType = varchar("problemtype", 50)
    val description = varchar("description", 1000)
    val requestId = integer("requestid").autoIncrement("seq")
    val isDone = bool("isdone")
}