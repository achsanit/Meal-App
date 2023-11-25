package com.achsanit.mealapp.data.response

import com.google.gson.annotations.SerializedName

data class ListMealByCategoryResponse(

	@field:SerializedName("meals")
	val meals: List<MealsItem?>? = null
)

data class MealsItem(

	@field:SerializedName("strMealThumb")
	val strMealThumb: String? = null,

	@field:SerializedName("idMeal")
	val idMeal: String? = null,

	@field:SerializedName("strMeal")
	val strMeal: String? = null
)
