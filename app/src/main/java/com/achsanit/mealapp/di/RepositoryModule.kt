package com.achsanit.mealapp.di

import com.achsanit.mealapp.data.Repository
import com.achsanit.mealapp.data.local.LocalDataSource
import com.achsanit.mealapp.data.network.RemoteDataSource
import com.achsanit.mealapp.ui.home.category.MealCategoryViewModel
import com.achsanit.mealapp.ui.home.listmeal.MealsViewModel
import com.achsanit.mealapp.ui.login.LoginViewModel
import com.achsanit.mealapp.ui.users.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoModule = module {
    single { RemoteDataSource(get(), get()) }
    single { LocalDataSource(get()) }
    single { Repository(get(),get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { UsersViewModel(get()) }
    viewModel { MealCategoryViewModel(get()) }
    viewModel { MealsViewModel(get()) }
}