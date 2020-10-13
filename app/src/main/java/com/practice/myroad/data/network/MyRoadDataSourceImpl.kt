package com.practice.myroad.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.practice.myroad.data.network.response.RoadResponse
import com.practice.myroad.internal.NoConnectivityException

private const val TAG = "MyRoadDataSourceImpl"
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
        } catch (e: NoConnectivityException) {
            Log.e(TAG, "fetchRoadData: No Internet Connection", e)
        }
    }
}