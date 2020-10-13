package com.practice.myroad.data.network

import androidx.lifecycle.LiveData
import com.practice.myroad.data.network.response.RoadResponse

interface MyRoadDataSource {
    val downloadedRoadData: LiveData<List<RoadResponse>>

    suspend fun fetchRoadData(roadId:String)
}