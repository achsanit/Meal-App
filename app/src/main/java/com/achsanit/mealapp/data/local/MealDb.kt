package com.achsanit.mealapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.achsanit.mealapp.data.response.MealByIdItem

@Database(
    entities = [MealByIdItem::class],
    version = 1,
    exportSchema = false
)
abstract class MealDb: RoomDatabase() {
    abstract fun mealDao(): MealDao
}