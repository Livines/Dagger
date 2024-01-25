package com.example.daggerworkbench.data.api

import com.example.daggerworkbench.data.model.ApiResponse
import com.example.daggerworkbench.data.model.User
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ApiHelper {
    suspend fun getUsers(): Response<List<User>>
    suspend fun getRandomDog(): Response<ApiResponse>
    suspend fun stateFlowSample(): Flow<Int>
}