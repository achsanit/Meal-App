package com.achsanit.mealapp.ui.home.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.mealapp.data.Repository
import com.achsanit.mealapp.data.response.MealByIdItem
import com.achsanit.mealapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailMealViewModel(private val repository: Repository) : ViewModel() {

    private val _meal: MutableStateFlow<Resource<MealByIdItem?>> =
        MutableStateFlow(Resource.Loading())
    val meal = _meal.asStateFlow()

    fun getMealById(idMeal: String) {
        viewModelScope.launch {
            _meal.update { repository.getMealById(idMeal) }
        }
    }

    fun insertBookmarkMeal(data: MealByIdItem) {
        viewModelScope.launch {
            repository.insertBookmarkMeal(data)
        }
    }

    fun deleteBookmarkMeal(idMeal: Int) {
        viewModelScope.launch {
            repository.deleteBookmarkMeal(idMeal)
        }
    }

    fun getMealBookmarkById(idMeal: Int): LiveData<MealByIdItem> {
        return repository.getBookmarkMealById(idMeal)
    }

}