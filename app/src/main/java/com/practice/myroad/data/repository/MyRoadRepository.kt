package com.practice.myroad.data.repository

import androidx.lifecycle.LiveData
import com.practice.myroad.model.MyRoad

interface MyRoadRepository {

    val myRoadData:LiveData<MyRoad>
    suspend fun getCurrentRoad(query:String): LiveData<MyRoad>
}