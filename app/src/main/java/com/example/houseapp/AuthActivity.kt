package com.example.houseapp

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.houseapp.databinding.ActivityAuthBinding
import com.example.houseapp.utils.DatabaseConnection
import com.example.houseapp.utils.NetworkConnection


/**
 * Стартовое активити приложения, ответсвенное за авторизацию пользователя.
 */
class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectToDatabase()
        initializeUI()
    }

    private fun initializeUI() {
        DataBindingUtil.setContentView<ActivityAuthBinding>(this, R.layout.activity_auth)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container_auth) as NavHostFragment
        navHostFragment.navController
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