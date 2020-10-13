package com.practice.myroad.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(roadEntity: RoadEntity)

    @Query("select * from road_table where id = :roadName")
    fun getRoadData(roadName: String):LiveData<RoadEntity>
}