package com.example.houseapp.utils

import android.os.StrictMode
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseConnection {
    fun init() {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val jdbcUrl = DatabaseConfig.jdbcUrl
            val driver = DatabaseConfig.driver
            val user = DatabaseConfig.user
            val password = DatabaseConfig.password
            Database.connect(jdbcUrl, driver, user, password)
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
         newSuspendedTransaction(Dispatchers.IO) { block() }
}