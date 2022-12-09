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


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var currentUserId: String
    private lateinit var appContainer: AppContainer
    private val requestsViewModel by viewModels<RequestsViewModel> { appContainer.requestsViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (application as MyApplication).appContainer
        authorize()
        initializeUI()
        connectToDatabase()
    }

    override fun onStart() {
        super.onStart()
        requestsViewModel.userId = currentUserId
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun initializeUI() {
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
    }

    private fun authorize() {
        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            currentUserId = auth.currentUser!!.uid
        }
    }

    private fun connectToDatabase() {
        if (NetworkConnection.isNetworkAvailable(applicationContext)) {
            NetworkConnection.isNetworkAvailable = true
            DatabaseConnection.init()
        } else {
            Toast.makeText(this, "No Internet access available", Toast.LENGTH_LONG).show()
        }
    }
}