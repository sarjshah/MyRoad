package com.practice.myroad.ui

import android.app.Application
import androidx.lifecycle.*
import com.practice.myroad.data.db.MyRoadDatabase
import com.practice.myroad.data.network.ConnectivityInterceptorImpl
import com.practice.myroad.data.network.MyRoadApiService
import com.practice.myroad.data.network.MyRoadDataSourceImpl
import com.practice.myroad.data.repository.MyRoadRepository
import com.practice.myroad.data.repository.MyRoadRepositoryImpl
import com.practice.myroad.internal.LoadingState
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MyRoadViewModel(val myRoadRepository: MyRoadRepository): ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState


    val roadData = myRoadRepository.myRoadData


    fun getRoadData(query: String) {
        viewModelScope.launch {
            try {

                _loadingState.value = LoadingState.LOADING
                myRoadRepository.getCurrentRoad(query)
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }
}