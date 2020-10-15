package com.practice.myroad.ui

import android.app.Application
import androidx.lifecycle.*
import com.practice.myroad.data.db.MyRoadDatabase
import com.practice.myroad.data.network.ConnectivityInterceptorImpl
import com.practice.myroad.data.network.MyRoadApiService
import com.practice.myroad.data.network.MyRoadDataSourceImpl
import com.practice.myroad.data.repository.MyRoadRepository
import com.practice.myroad.data.repository.MyRoadRepositoryImpl
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MyRoadViewModel(val myRoadRepository: MyRoadRepository): ViewModel() {

    private val _roadQuery = MutableLiveData<String>()
    val roadQuery: LiveData<String>
    get() = _roadQuery


    val roadData = myRoadRepository.myRoadData


    fun getRoadData(query: String) {
        viewModelScope.launch {
            myRoadRepository.getCurrentRoad(query)
        }
    }
}