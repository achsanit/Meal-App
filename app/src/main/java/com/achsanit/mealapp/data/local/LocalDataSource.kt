package com.achsanit.mealapp.data.local

class LocalDataSource(
    private val dataStoreManager: DataStoreManager
) {

    suspend fun setLoginStatus(loginStatus: Boolean, token: String) {
        dataStoreManager.setLoginStatus(loginStatus, token)
    }

}