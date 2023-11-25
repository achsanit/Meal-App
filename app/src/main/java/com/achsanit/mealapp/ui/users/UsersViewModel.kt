package com.achsanit.mealapp.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.achsanit.mealapp.data.Repository
import kotlinx.coroutines.launch

class UsersViewModel(
    private val repository: Repository
): ViewModel() {

    fun getListUsers() = repository.getListUsers().cachedIn(viewModelScope).asLiveData()

    fun setLoginStatus(loginStatus: Boolean) {
        viewModelScope.launch {
            repository.setLoginStatus(loginStatus, "")
        }
    }

}