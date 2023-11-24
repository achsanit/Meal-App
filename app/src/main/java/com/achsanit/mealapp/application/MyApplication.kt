package com.achsanit.mealapp.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.achsanit.mealapp.di.KoinInitializer

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        KoinInitializer.init(this)
    }
}