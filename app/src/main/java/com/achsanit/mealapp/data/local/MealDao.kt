package com.achsanit.mealapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.achsanit.mealapp.data.response.MealByIdItem

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookmarkMeal(data: MealByIdItem)

    @Query("SELECT * FROM meal_table WHERE idMeal=:idMeal")
    fun getMealById(idMeal: Int): LiveData<MealByIdItem>

    @Query("SELECT * FROM meal_table")
    fun getAllBookmarkMeal(): LiveData<List<MealByIdItem>>

    @Query("DELETE FROM meal_table WHERE idMeal=:idMeal")
    suspend fun deleteBookmarkMeal(idMeal: Int)

    @Query("DELETE FROM meal_table")
    suspend fun deleteAllBookmarks()
}