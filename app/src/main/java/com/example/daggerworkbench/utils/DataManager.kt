package com.example.daggerworkbench.utils

import com.example.daggerworkbench.data.model.User

//Singleton
object DataManager{
    init {
        println("Singleton class invoked.")
    }
    fun getUserData(): User {
        // some code
        return User(1)
    }

}