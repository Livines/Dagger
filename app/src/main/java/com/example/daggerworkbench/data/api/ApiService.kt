package com.example.daggerworkbench.data.api

import com.example.daggerworkbench.data.model.ApiResponse
import com.example.daggerworkbench.data.model.User
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
    @GET("random")
    suspend fun getRandomDog(): Response<ApiResponse>
}