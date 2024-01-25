package com.example.daggerworkbench.data.repository

import com.example.daggerworkbench.data.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers() =  apiHelper.getUsers()
    //fun getRemoteData() = apiHelper.getRandomDog()
    suspend fun getRemoteData() = apiHelper.getRandomDog()

    suspend fun stateFlowSample() = apiHelper.stateFlowSample()

}