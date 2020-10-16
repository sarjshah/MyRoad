package com.practice.myroad.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.practice.myroad.data.network.response.RoadResponse
import com.practice.myroad.internal.NoConnectivityException
import com.practice.myroad.internal.NonExistentRoadException
import timber.log.Timber

class MyRoadDataSourceImpl (
    private val myRoadApiService: MyRoadApiService
) : MyRoadDataSource {
    private val _downloadedRoadData = MutableLiveData<List<RoadResponse>>()
    override val downloadedRoadData: LiveData<List<RoadResponse>>
        get() = _downloadedRoadData

    override suspend fun fetchRoadData(roadId: String) {
        try {
            val fetchedRoadData = myRoadApiService
                .getRoad(roadId).await()
            _downloadedRoadData.postValue(fetchedRoadData)
        } catch (e: Exception) {
            when (e) {
                is NoConnectivityException -> {
                    Timber.e("fetchRoadData: No Internet Connection")
                }
                is NonExistentRoadException -> {
                    Timber.e("fetchRoadData: Invalid Road given")
                }
                else -> {
                    Timber.e("fetchRoadData: Unknown Error")
                }
            }
            throw e
        }
    }
}