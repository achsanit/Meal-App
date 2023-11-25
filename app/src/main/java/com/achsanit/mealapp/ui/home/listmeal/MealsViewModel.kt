package com.achsanit.mealapp.ui.home.listmeal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.mealapp.data.Repository
import com.achsanit.mealapp.data.response.MealsItem
import com.achsanit.mealapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealsViewModel(
    private val repo: Repository
) : ViewModel() {

    private val _meals: MutableStateFlow<Resource<List<MealsItem?>?>> =
        MutableStateFlow(Resource.Loading())
    val meals = _meals.asStateFlow()

    fun getMeasByCategory(category: String) {
        viewModelScope.launch {
            _meals.update { repo.getMealsByCategory(category) }
        }
    }

}