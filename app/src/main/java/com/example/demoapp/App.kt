package com.example.demoapp

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        var mContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }
}
