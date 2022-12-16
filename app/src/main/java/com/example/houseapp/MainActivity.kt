package com.example.houseapp

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.houseapp.ui.homescreen.UserViewModel
import com.example.houseapp.ui.listscreen.RequestsViewModel
import com.example.houseapp.ui.loginscreen.LoginActivity
import com.example.houseapp.utils.AppContainer
import com.example.houseapp.utils.DatabaseConnection
import com.example.houseapp.utils.NetworkConnection
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var currentUserId: String
    private lateinit var appContainer: AppContainer
    private val requestsViewModel by viewModels<RequestsViewModel> { appContainer.requestsViewModelFactory }
    private val userViewModel by viewModels<UserViewModel> { appContainer.userViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            appContainer = (application as MyApplication).appContainer
            authorize()
            initializeUI()
            connectToDatabase()
        } catch (ex: Exception) {
            Log.e(javaClass.simpleName, ex.message.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        requestsViewModel.userId = currentUserId
        userViewModel.userId = currentUserId
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (view is EditText) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    view.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun initializeUI() {
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)

        setSupportActionBar(toolbar)

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