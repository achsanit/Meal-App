package com.achsanit.mealapp.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.achsanit.mealapp.data.local.LocalDataSource
import com.achsanit.mealapp.data.network.RemoteDataSource
import com.achsanit.mealapp.data.response.CategoriesItem
import com.achsanit.mealapp.data.response.DataItem
import com.achsanit.mealapp.data.response.LoginResponse
import com.achsanit.mealapp.data.response.MealByIdItem
import com.achsanit.mealapp.data.response.MealsItem
import com.achsanit.mealapp.utils.Resource
import com.achsanit.mealapp.utils.resourceMapper
import kotlinx.coroutines.flow.Flow

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun login(email: String, password: String): Resource<LoginResponse> {
        return resourceMapper {
            remoteDataSource.login(email, password)
        }
    }

    suspend fun setLoginStatus(loginStatus: Boolean, token: String) {
        localDataSource.setLoginStatus(loginStatus, token)
    }

    fun getListUsers(): Flow<PagingData<DataItem>> = remoteDataSource.getListUser()

    suspend fun getCategories(): Resource<List<CategoriesItem?>?> {
        return resourceMapper {
            remoteDataSource.getListCategory()
        }
    }

    suspend fun getMealsByCategory(category: String): Resource<List<MealsItem?>?> {
        return resourceMapper {
            remoteDataSource.getMealsByCategory(category)
        }
    }

    suspend fun getMealById(idMeal: String): Resource<MealByIdItem?> {
        return resourceMapper {
            remoteDataSource.getMealById(idMeal)
        }
    }

    suspend fun insertBookmarkMeal(data: MealByIdItem) {
        return localDataSource.insertBookmarkMeal(data)
    }

    suspend fun deleteBookmarkMeal(idMeal: Int) {
        return localDataSource.deleteBookmarkMeal(idMeal)
    }

    fun getBookmarkMealById(idMeal: Int): LiveData<MealByIdItem> {
        return localDataSource.getBookmarkById(idMeal)
    }

    fun getListBookmarkMeal(): LiveData<List<MealByIdItem>> {
        return localDataSource.getListBookmarkMeal()
    }

    suspend fun deleteAllBookmark() {
        return localDataSource.deleteAllBookmark()
    }
}