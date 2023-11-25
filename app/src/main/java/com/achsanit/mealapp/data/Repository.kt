package com.achsanit.mealapp.data

import androidx.paging.PagingData
import com.achsanit.mealapp.data.local.LocalDataSource
import com.achsanit.mealapp.data.network.RemoteDataSource
import com.achsanit.mealapp.data.response.CategoriesItem
import com.achsanit.mealapp.data.response.DataItem
import com.achsanit.mealapp.data.response.LoginResponse
import com.achsanit.mealapp.data.response.MealsItem
import com.achsanit.mealapp.utils.Resource
import com.achsanit.mealapp.utils.resourceMapper
import kotlinx.coroutines.flow.Flow

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun login(email: String, password: String): Resource<LoginResponse> {
        return resourceMapper {
            remoteDataSource.login(email, password)
        }
    }

    suspend fun setLoginStatus(loginStatus: Boolean, token: String) {
        localDataSource.setLoginStatus(loginStatus, token)
    }

    fun getListUsers(): Flow<PagingData<DataItem>> = remoteDataSource.getListUser()

    suspend fun getCategories(): Resource<List<CategoriesItem?>?> {
        return resourceMapper {
            remoteDataSource.getListCategory()
        }
    }

    suspend fun getMealsByCategory(category: String): Resource<List<MealsItem?>?> {
        return resourceMapper {
            remoteDataSource.getMealsByCategory(category)
        }
    }

}