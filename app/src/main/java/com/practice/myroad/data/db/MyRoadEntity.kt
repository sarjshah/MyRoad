package com.practice.myroad.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "road_table")
class MyRoadEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id:String,
    @ColumnInfo(name = "displayName")
    var displayName:String,
    @ColumnInfo(name = "statusSeverity")
    var statusSeverity:String,
    @ColumnInfo(name = "statusSeverityDescription")
    var statusSeverityDescription: String)


