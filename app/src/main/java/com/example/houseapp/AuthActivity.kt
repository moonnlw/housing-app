package com.example.houseapp

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.houseapp.databinding.ActivityAuthBinding
import com.example.houseapp.utils.DatabaseConnection
import com.example.houseapp.utils.NetworkConnection


class AuthActivity : AppCompatActivity() {

    private val authViewModel by viewModels<AuthViewModel> {
        (application as MyApplication).appContainer.viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectToDatabase()
        observeData()
    }

    private fun initializeUI() {
        DataBindingUtil.setContentView<ActivityAuthBinding>(this, R.layout.activity_auth)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container_auth) as NavHostFragment
        navHostFragment.navController
    }

    /**
     * Подписывается на isAuthorized в [authViewModel], задерживая отрисовку UI.
     * Если пользователь авторизован: Intent на [UserActivity]/[AdminActivity] в зависимости от роли
     * Иначе: отрисовываем UI для дальнейшей авторизации
     */
    private fun observeData() {
        authViewModel.isAuthorized.observe(this) { isAuth ->
            if (isAuth) {
                val intent = Intent(
                    application,
                    if (authViewModel.isAdmin) AdminActivity::class.java else UserActivity::class.java
                )
                startActivity(intent)
                this.finish()
            } else {
                initializeUI()
            }
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

    private fun connectToDatabase() {
        if (NetworkConnection.isNetworkAvailable(applicationContext)) {
            DatabaseConnection.init()
        } else {
            Toast.makeText(this, "No Internet access available", Toast.LENGTH_LONG).show()
        }
    }
}