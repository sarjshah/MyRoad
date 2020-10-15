package com.practice.myroad.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyRoadEntity::class], version = 1, exportSchema = false)
abstract class MyRoadDatabase: RoomDatabase() {
    abstract fun roadDao():MyRoadDao
}