package com.practice.myroad.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.practice.myroad.data.db.MyRoadDao
import com.practice.myroad.data.db.MyRoadEntityMapperImpl
import com.practice.myroad.data.network.MyRoadDataSource
import com.practice.myroad.data.network.response.RoadResponse
import com.practice.myroad.data.network.response.RoadResponseMapperImpl
import com.practice.myroad.internal.NoConnectivityException
import com.practice.myroad.model.MyRoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import timber.log.Timber
import java.nio.channels.NotYetConnectedException

class MyRoadRepositoryImpl(
    private val myRoadDao: MyRoadDao,
    private val roadDataSource: MyRoadDataSource
) : MyRoadRepository {

    private var _myRoadData = MutableLiveData<MyRoad>()
    override val myRoadData:LiveData<MyRoad>
    get() = _myRoadData

    init {
        roadDataSource.downloadedRoadData.observeForever{newRoad ->
            persistFetchedRoadData(newRoad)
        }
    }
    override suspend fun getCurrentRoad(roadQuery: String): LiveData<MyRoad> {
        return withContext(Dispatchers.IO) {
            try {
                fetchRoadData(roadQuery)
            } catch (e: Exception) {
                when(e) {
                    is NoConnectivityException -> {
                        fetchRoadFromDatabase(roadQuery)
                        Timber.e("No Network")
                        throw e
                    }
                    else -> throw e
                }
            }
            fetchRoadFromDatabase(roadQuery)
            return@withContext myRoadData
        }
    }

    private fun fetchRoadFromDatabase(roadQuery:String) {
        _myRoadData.postValue(
            MyRoadEntityMapperImpl().toDomainModel(
                myRoadDao.getRoadData(
                    roadQuery.toLowerCase()
                )
            )
        )
    }

    private suspend fun fetchRoadData(roadQuery: String) {
        roadDataSource.fetchRoadData(roadQuery)
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime):Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private fun persistFetchedRoadData(fetchedRoadData: List<RoadResponse>) {
        GlobalScope.launch(Dispatchers.IO) {
            val roadData = RoadResponseMapperImpl().toEntityList(fetchedRoadData)[0]
            myRoadDao.upsert(roadData)
        }
    }
}