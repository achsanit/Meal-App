package com.achsanit.mealapp.data.network

import com.achsanit.mealapp.data.response.ListMealCategoryResponse
import retrofit2.http.GET

interface MealService {

    @GET("categories.php")
    suspend fun getCategories(): ListMealCategoryResponse

}