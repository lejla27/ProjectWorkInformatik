package com.example.projectworkmap.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")
data class City(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "x_coordinate")
    val x: Int,
    @ColumnInfo(name = "y_coordinate")
    val y: Int,
    @ColumnInfo(name = "image")
    val image: String

)
