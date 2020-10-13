package com.practice.myroad.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RoadEntity::class], version = 1)
abstract class RoadDatabase: RoomDatabase() {
    abstract fun roadDao():RoadDao

    companion object {
        @Volatile private var instance:RoadDatabase ?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RoadDatabase::class.java,
                "road.db"
            ).build()



    }
}