package com.achsanit.mealapp.di

import com.achsanit.mealapp.data.local.DataStoreManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single { DataStoreManager(androidContext()) }
}