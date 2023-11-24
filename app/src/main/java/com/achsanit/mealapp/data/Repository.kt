package com.achsanit.mealapp.data

import androidx.paging.PagingData
import com.achsanit.mealapp.data.local.LocalDataSource
import com.achsanit.mealapp.data.network.RemoteDataSource
import com.achsanit.mealapp.data.response.DataItem
import com.achsanit.mealapp.data.response.LoginResponse
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

}