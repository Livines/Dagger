package com.example.daggerworkbench.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggerworkbench.data.model.ApiResponse
import com.example.daggerworkbench.data.model.User
import com.example.daggerworkbench.data.repository.MainRepository
import com.example.daggerworkbench.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val dogResponse = MutableLiveData<ApiResponse>()
    val error = MutableLiveData<String>()

    fun getRandomDogs() {
        viewModelScope.launch {
            val resposne=repository.getRemoteData()
            Log.d("=========","RESPONSE======"+resposne.message())
        }
    }
    private val _users = MutableLiveData<Resource<List<User>>>()
    private val mutableState = MutableStateFlow<Resource<List<User>>>(Resource.success(emptyList()))

    private val mutableStateInt = MutableStateFlow<Int>(0)
    val stateFlowInt:StateFlow<Int> = mutableStateInt

    private val mutableSharedInt = MutableSharedFlow<Int>(0)
    val SharedFlowInt:SharedFlow<Int> = mutableSharedInt
    val users: LiveData<Resource<List<User>>>
        get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _users.postValue(Resource.loading(null))
                repository.getUsers().let {
                    if (it.isSuccessful) {
                        _users.postValue(Resource.success(it.body()))
                        mutableState.value= Resource.success(it.body())
                    } else _users.postValue(Resource.error(it.errorBody().toString(), null))
                }

        }
        test_withContext()
    }
    /*

    WithContext usage
     */
    fun test_withContext(){
        viewModelScope.launch {
            Log.d("ViewModel", "Just viewModelScope: ${Thread.currentThread().name}")
        }
// Output: Just viewModelScope: main

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ViewModel", "IO viewModelScope: ${Thread.currentThread().name}")
        }
// Output: IO viewModelScope: DefaultDispatcher-worker-3

        viewModelScope.launch {
            Log.d("ViewModel", "viewModelScope thread: ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {
                delay(3000)
                Log.d("ViewModel", "withContext thread: ${Thread.currentThread().name}")
            }
            Log.d("ViewModel", "I'm finished!")
        }
    }
  suspend fun stateFlowSample(){
     repository.stateFlowSample().collect{
          mutableStateInt.value= it
         Log.d("EMIT STATE FLOW=====", it.toString())
      }
      val item= repository.stateFlowSample()
      item.collect {
          mutableSharedInt.emit(it)
          Log.d("EMIT SHARED FLOW=====", it.toString())
      }
    }

}
