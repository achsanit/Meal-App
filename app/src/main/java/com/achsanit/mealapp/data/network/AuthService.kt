package com.achsanit.mealapp.data.network

import com.achsanit.mealapp.data.response.ListUsersResponse
import com.achsanit.mealapp.data.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface AuthService {

    @POST("login")
    suspend fun login(
        @Body body: HashMap<String, String>
    ): LoginResponse

    @GET("users")
    suspend fun getListUsers(
        @QueryMap query: HashMap<String, String>
    ): ListUsersResponse
}