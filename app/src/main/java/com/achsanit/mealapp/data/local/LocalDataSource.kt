package com.achsanit.mealapp.data.local

import androidx.lifecycle.LiveData
import com.achsanit.mealapp.data.response.MealByIdItem

class LocalDataSource(
    private val dataStoreManager: DataStoreManager,
    private val mealDao: MealDao
) {

    suspend fun setLoginStatus(loginStatus: Boolean, token: String) {
        dataStoreManager.setLoginStatus(loginStatus, token)
    }

    suspend fun insertBookmarkMeal(data: MealByIdItem) {
        mealDao.insertBookmarkMeal(data)
    }

    suspend fun deleteBookmarkMeal(idMeal: Int) {
        mealDao.deleteBookmarkMeal(idMeal)
    }

    fun getBookmarkById(idMeal: Int): LiveData<MealByIdItem> {
        return mealDao.getMealById(idMeal)
    }

    fun getListBookmarkMeal(): LiveData<List<MealByIdItem>> {
        return mealDao.getAllBookmarkMeal()
    }

    suspend fun deleteAllBookmark() {
        return mealDao.deleteAllBookmarks()
    }
}