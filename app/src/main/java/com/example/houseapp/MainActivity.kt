package com.example.houseapp

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object UserRequests : Table() {
    val userId = integer("userid")
    val type = varchar("problemtype", 50).nullable()
    val description = varchar("description", 1000).nullable()
    val requestId = integer("requestid").autoIncrement("seq")
    val isDone = bool("isdone")
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //For Internet connection with database
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createDatabaseConnection()
    }

    fun displayButtonOnClick(view: View) {
        try {
            transaction {
                addLogger()
                UserRequests.insert {
                    it[userId] = 24
                    it[type] = display(ProblemType.Electricity)
                    it[description] = "Не работает лампа"
                    it[isDone] = false
                }
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    private fun createDatabaseConnection() {
        val jdbcUrl = "jdbc:postgresql://ec2-3-216-167-65.compute-1.amazonaws.com:5432/d5414vl6ij5i2f?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory"
        val driver = "org.postgresql.Driver"
        val user = "avinugmjzprnkv"
        val password = "525799763887e66a60857bb4b059e013cc650bb9dbf86c28077ed7235a7ca159"
        Database.connect(jdbcUrl, driver, user, password)
    }
}