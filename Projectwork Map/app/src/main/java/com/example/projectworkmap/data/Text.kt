package com.example.projectworkmap.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "text_table")
data class Text(
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "avatar")
    val avatar: String,  //if avatar is an image then the data type must be Int!

)
