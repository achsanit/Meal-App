package com.achsanit.mealapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.mealapp.data.Repository
import com.achsanit.mealapp.data.response.LoginResponse
import com.achsanit.mealapp.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _loginStatus: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginStatus: LiveData<Resource<LoginResponse>> = _loginStatus

    fun login(email: String, password: String) {
        _loginStatus.postValue(Resource.Loading())
        viewModelScope.launch {
            _loginStatus.postValue(repository.login(email, password))
        }
    }

    fun setLoginStatus(loginStatus: Boolean, token: String) {
        viewModelScope.launch {
            repository.setLoginStatus(loginStatus, token)
        }
    }

}