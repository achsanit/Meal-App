package com.achsanit.mealapp.di

import androidx.room.Room
import com.achsanit.mealapp.data.local.DataStoreManager
import com.achsanit.mealapp.data.local.MealDb
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single { DataStoreManager(androidContext()) }
}

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MealDb::class.java,
            "meal_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    factory { get<MealDb>().mealDao() }
}