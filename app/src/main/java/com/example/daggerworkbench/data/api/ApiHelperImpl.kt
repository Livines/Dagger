package com.example.daggerworkbench.data.api

import com.example.daggerworkbench.data.model.ApiResponse
import com.example.daggerworkbench.data.model.User
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<List<User>> = apiService.getUsers()
    override suspend fun getRandomDog()= apiService.getRandomDog()
    override suspend fun stateFlowSample(): Flow<Int> {
       val flow:Flow<Int> = flow {
           for (i in 0 until 10){
               emit(i)
           }
       }
        return flow
    }

}