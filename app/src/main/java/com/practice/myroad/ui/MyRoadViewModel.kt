package com.practice.myroad.ui

import android.provider.SyncStateContract
import androidx.lifecycle.*
import com.practice.myroad.data.repository.MyRoadRepository
import com.practice.myroad.internal.LoadingState
import com.practice.myroad.internal.NoConnectivityException
import com.practice.myroad.internal.NonExistentRoadException
import com.practice.myroad.utils.Constants
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
                when(e) {
                    is NoConnectivityException -> LoadingState.error(Constants.NO_CONNECTIVITY_MESSAGE)
                    is NonExistentRoadException -> LoadingState.error(Constants.CANNOT_FIND_ROAD_MESSAGE)
                    else -> _loadingState.value = LoadingState.error(e.message)
                }
            }
        }
    }
}