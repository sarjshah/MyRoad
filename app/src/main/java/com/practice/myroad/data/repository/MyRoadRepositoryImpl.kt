package com.practice.myroad.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.practice.myroad.data.db.MyRoadDao
import com.practice.myroad.data.db.MyRoadEntityMapperImpl
import com.practice.myroad.data.network.MyRoadDataSource
import com.practice.myroad.data.network.response.RoadResponse
import com.practice.myroad.data.network.response.RoadResponseMapperImpl
import com.practice.myroad.model.MyRoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

private const val TAG = "MyRoadRepositoryImpl"
class MyRoadRepositoryImpl(
    private val myRoadDao: MyRoadDao,
    private val roadDataSource: MyRoadDataSource
) : MyRoadRepository {

    private var _roadData = MutableLiveData<MyRoad>()
    override val myRoadData:LiveData<MyRoad>
    get() = _roadData

    init {
        roadDataSource.downloadedRoadData.observeForever{newRoad ->
            persistFetchedRoadData(newRoad)
        }
    }
    override suspend fun getCurrentRoad(roadQuery: String): LiveData<MyRoad> {
        return withContext(Dispatchers.IO) {
            fetchRoadData(roadQuery)
            _roadData.postValue(MyRoadEntityMapperImpl().toDomainModel(myRoadDao.getRoadData(roadQuery.toLowerCase())))
             return@withContext myRoadData
        }
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