package com.practice.myroad.internal.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.practice.myroad.data.repository.MyRoadRepository
import com.practice.myroad.utils.Constants
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber

class RefreshDataWorker(appContext: Context, parameters: WorkerParameters):
    CoroutineWorker(appContext, parameters), KoinComponent {
    companion object {
        const val WORK_NAME = "com.practice.myroad.internal.work.RefreshDataWorker"
    }

    private val myRoadRepository:MyRoadRepository by inject()

    override suspend fun doWork(): Result {
        try {
            myRoadRepository.getCurrentRoad(Constants.USER_DEFAULT_ROAD)
            Timber.i( "doWork: Work request for road sync has run")
            return Result.success()
        } catch (e: HttpException) {
            return Result.retry()
        }
    }
}