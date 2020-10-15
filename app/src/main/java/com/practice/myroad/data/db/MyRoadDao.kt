package com.practice.myroad.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyRoadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(myRoadEntity: MyRoadEntity)

    @Query("select * from road_table where id = :roadName")
    fun getRoadData(roadName: String):MyRoadEntity
}