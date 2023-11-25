package com.achsanit.mealapp.data.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.achsanit.mealapp.data.response.CategoriesItem
import com.achsanit.mealapp.data.response.DataItem
import com.achsanit.mealapp.data.response.LoginResponse
import com.achsanit.mealapp.data.response.MealsItem
import kotlinx.coroutines.flow.Flow


class RemoteDataSource(
    private val authService: AuthService,
    private val mealService: MealService
) {

    suspend fun login(email: String, password: String): LoginResponse {
        val body = HashMap<String, String>()
        body["email"] = email
        body["password"] = password

        return authService.login(body)
    }

    fun getListUser(): Flow<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(6, enablePlaceholders = false),
            pagingSourceFactory = {
                UsersPagingSource(service = authService)
            }
        ).flow
    }

    suspend fun getListCategory(): List<CategoriesItem?>? {
        return mealService.getCategories().categories
    }

    suspend fun getMealsByCategory(category: String): List<MealsItem?>? {
        val query = HashMap<String, String>()
        query["c"] = category

        return mealService.getMealsByCategory(query).meals
    }

}