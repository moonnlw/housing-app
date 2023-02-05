package com.example.houseapp.ui.user

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.houseapp.AuthActivityDirections
import com.example.houseapp.AuthViewModel
import com.example.houseapp.MyApplication
import com.example.houseapp.R
import com.example.houseapp.AuthActivity
import com.example.houseapp.databinding.ActivityMainUserBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Главное активити пользователя, отрисовывает UI для пользователя
 */
class UserActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val authViewModel: AuthViewModel by viewModels {
        (application as MyApplication).appContainer.viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(this.javaClass.simpleName, "AuthActivity onCreate() starts")
        initializeUI()
        observeAuthorizationStatus()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun initializeUI() {
        val binding = ActivityMainUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container_user) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)

        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.profileScreen, R.id.requestsList, R.id.createRequest)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            bottomNavigationView.visibility =
                when (destination.id) {
                    R.id.requestUser -> View.GONE
                    else -> View.VISIBLE
                }
        }

        /**
         * Передаем userId в каждое из назначений графа
         */
        val args = bundleOf("userId" to authViewModel.userId)
        navController.setGraph(R.navigation.nav_graph_user, args)

        bottomNavigationView.setOnItemReselectedListener { }
        bottomNavigationView.setOnItemSelectedListener { item ->
            navController.navigate(item.itemId, args)
            true
        }
    }

    /**
     * В случае окончания сессии -> навигация к [AuthActivity]
     */
    private fun observeAuthorizationStatus() {
        authViewModel.userIdLiveData.observe(this) {
            val action = AuthActivityDirections.navigateToAuthActivity()
            if (it.isNullOrEmpty()) navController.navigate(action)
        }
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
}