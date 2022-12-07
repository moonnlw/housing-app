package com.example.houseapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.houseapp.utils.DatabaseConnection
import com.example.houseapp.listscreen.RequestsViewModel
import com.example.houseapp.loginscreen.LoginActivity
import com.example.houseapp.utils.NetworkConnection
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.example.houseapp.listscreen.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction


class MainActivity : AppCompatActivity() {
    private val viewModel: UserViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUserId: String
    private lateinit var appContainer: AppContainer
    private val viewModel: RequestsViewModel by viewModels { appContainer.requestsViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (application as MyApplication).appContainer

        //createDatabaseConnection()
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            currentUserId = auth.currentUser!!.uid
            val currentUserId = auth.currentUser!!.uid

            /*var isAdmin = false
            transaction {
                isAdmin = Roles.select { Roles.userId eq currentUserId }.single()[Roles.isAdmin]
            }

            if (isAdmin) {
                Toast.makeText(this, "Admin", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "User", Toast.LENGTH_LONG).show()
            }*/

            viewModel.setUserId(currentUserId);
        }

        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.profileScreen, R.id.requestsList, R.id.createRequest)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            bottomNavigationView.visibility =
                when (destination.id) {
                    R.id.request -> View.GONE
                    else -> View.VISIBLE
                }
        }

        window.statusBarColor = Color.TRANSPARENT

        if (NetworkConnection.isNetworkAvailable(applicationContext)) {
            NetworkConnection.isNetworkAvailable = true
            DatabaseConnection.init()
        } else {
            Toast.makeText(this, "No Internet access available", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.userId = currentUserId
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}