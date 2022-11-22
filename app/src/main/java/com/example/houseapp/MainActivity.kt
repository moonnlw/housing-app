package com.example.houseapp

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createDatabaseConnection()
    }

    fun displayButtonOnClick(view: View) {
        sendRequest(createRequest())
    }

    private fun createDatabaseConnection() {
        //For Internet connection with database
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val jdbcUrl = "jdbc:postgresql://ec2-3-216-167-65.compute-1.amazonaws.com:5432/d5414vl6ij5i2f?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory"
        val driver = "org.postgresql.Driver"
        val user = "avinugmjzprnkv"
        val password = "525799763887e66a60857bb4b059e013cc650bb9dbf86c28077ed7235a7ca159"
        Database.connect(jdbcUrl, driver, user, password)
    }

    private fun createRequest() : UserRequest {
        val userId = 1;
        val problemType = ProblemType.Other;
        val text = "some text";
        return UserRequest(userId, problemType, text);
    }

    private fun sendRequest(request : UserRequest) = runBlocking {
        try {
            transaction {
                addLogger()
                UserRequests.insert {
                    it[userId] = request.userId
                    it[type] = display(request.problemType)
                    it[description] = request.text
                    it[isDone] = request.isDone
                }
            }
        } catch (e: Exception) {
            println(e)
        }
    }
}