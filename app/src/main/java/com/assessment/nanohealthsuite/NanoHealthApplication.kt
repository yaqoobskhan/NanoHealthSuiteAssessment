package com.assessment.nanohealthsuite

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.assessment.nanohealthsuite.utility.Constant


class NanoHealthApplication : Application() {

    companion object {
        lateinit var appContext: Context
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        initialize()
    }

    private fun initialize() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        sharedPreferences =
            applicationContext.getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)
    }

}