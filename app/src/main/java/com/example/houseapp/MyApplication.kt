package com.example.houseapp

import android.app.Application
import android.content.Context
import com.example.houseapp.utils.AppContainer

class MyApplication: Application() {

    private lateinit var mContext: Context

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()

        mContext = applicationContext
        appContainer = AppContainer(mContext)
    }
}