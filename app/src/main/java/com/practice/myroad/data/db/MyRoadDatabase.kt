package com.practice.myroad.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyRoadEntity::class], version = 1, exportSchema = false)
abstract class MyRoadDatabase: RoomDatabase() {
    abstract fun roadDao():MyRoadDao
}