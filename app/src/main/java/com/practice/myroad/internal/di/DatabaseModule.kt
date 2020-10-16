package com.practice.myroad.internal.di

import android.app.Application
import androidx.room.Room
import com.practice.myroad.data.db.MyRoadDao
import com.practice.myroad.data.db.MyRoadDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val databaseModule = module {

    fun provideDatabase(application: Application): MyRoadDatabase {
        return Room.databaseBuilder(application, MyRoadDatabase::class.java, "road.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    fun provideDao(databaseMy: MyRoadDatabase): MyRoadDao {
        return databaseMy.roadDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

