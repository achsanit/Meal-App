package com.achsanit.mealapp.data.network

import com.achsanit.mealapp.data.response.ListMealByCategoryResponse
import com.achsanit.mealapp.data.response.ListMealCategoryResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MealService {

    @GET("categories.php")
    suspend fun getCategories(): ListMealCategoryResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @QueryMap query: HashMap<String, String>
    ): ListMealByCategoryResponse
}