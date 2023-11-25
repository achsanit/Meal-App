package com.achsanit.mealapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ListMealByCategoryResponse(

	@field:SerializedName("meals")
	val meals: List<MealsItem?>? = null
)

@Parcelize
data class MealsItem(

	@field:SerializedName("strMealThumb")
	val strMealThumb: String? = null,

	@field:SerializedName("idMeal")
	val idMeal: String? = null,

	@field:SerializedName("strMeal")
	val strMeal: String? = null
): Parcelable
