package com.achsanit.mealapp.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.mealapp.data.Repository
import com.achsanit.mealapp.data.response.MealByIdItem
import kotlinx.coroutines.launch

class BookmarkMealViewModel(private val repository: Repository) : ViewModel() {
    fun getListBookmark(): LiveData<List<MealByIdItem>> {
        return repository.getListBookmarkMeal()
    }

    fun deleteAllBookmarkMeal() {
        viewModelScope.launch {
            repository.deleteAllBookmark()
        }
    }
}