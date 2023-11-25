package com.achsanit.mealapp.utils

import com.achsanit.mealapp.data.response.MealByIdItem
import com.achsanit.mealapp.data.response.MealsItem

fun List<MealByIdItem>.mapToMealsItem(): List<MealsItem> {
    return this.map {
        MealsItem(
            strMealThumb = it.strMealThumb,
            strMeal = it.strMeal,
            idMeal = it.idMeal
        )
    }
}