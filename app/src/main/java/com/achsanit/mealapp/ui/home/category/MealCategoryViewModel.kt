package com.achsanit.mealapp.ui.home.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.mealapp.data.Repository
import com.achsanit.mealapp.data.response.CategoriesItem
import com.achsanit.mealapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealCategoryViewModel(private val repo: Repository) : ViewModel() {

    private val _listCategories: MutableStateFlow<Resource<List<CategoriesItem?>?>> =
        MutableStateFlow(Resource.Loading())
    val listCategories = _listCategories.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            _listCategories.update { repo.getCategories() }
        }
    }

}