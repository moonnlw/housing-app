package com.example.houseapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDatabaseConnection()
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val currentUserId = auth.currentUser!!.uid

            var isAdmin = false
            transaction {
                isAdmin = Roles.select { Roles.userId eq currentUserId }.single()[Roles.isAdmin]
            }

            if (isAdmin) {
                Toast.makeText(this, "Admin", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "User", Toast.LENGTH_LONG).show()
            }

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
                when(destination.id) {
                    R.id.request -> View.GONE
                    else -> View.VISIBLE
                }
        }

/*        window.statusBarColor = ContextCompat.getColor(
            this, com.google.android.material.R.attr.colorSecondaryContainer
        )*/

        /*window.statusBarColor = ContextCompat.getColor(this, com.google.android.material.R.attr.colorSurface);*/
        //window.navigationBarColor = ContextCompat.getColor(this,R.color.purple_500);

    /*val color = SurfaceColors.SURFACE_2.getColor(this)
        window.statusBarColor = color
        window.navigationBarColor = color*/

        window.statusBarColor = Color.TRANSPARENT
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun createDatabaseConnection() {
        //For Internet connection with database
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val jdbcUrl =
            "jdbc:postgresql://ec2-3-216-167-65.compute-1.amazonaws.com:5432/d5414vl6ij5i2f?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory"
        val driver = "org.postgresql.Driver"
        val user = "avinugmjzprnkv"
        val password = "525799763887e66a60857bb4b059e013cc650bb9dbf86c28077ed7235a7ca159"
        try {
            Database.connect(jdbcUrl, driver, user, password)
        }
        catch(e : Exception) {
            println(e)
            Toast.makeText(this, "Check your Internet connection", Toast.LENGTH_LONG).show()
        }
    }
}