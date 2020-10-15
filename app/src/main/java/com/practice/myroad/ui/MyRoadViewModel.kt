package com.practice.myroad.ui

import androidx.lifecycle.*
import com.practice.myroad.data.repository.MyRoadRepository
import com.practice.myroad.internal.LoadingState
import kotlinx.coroutines.launch

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