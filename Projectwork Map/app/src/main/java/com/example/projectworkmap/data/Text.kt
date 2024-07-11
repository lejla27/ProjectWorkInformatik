package com.example.projectworkmap.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "text_table")
data class Text(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @ColumnInfo(name = "avatar")
    val avatar: String,  //if avatar is an image then the data type must be Int!
    @ColumnInfo(name = "description")
    val text: String
)
