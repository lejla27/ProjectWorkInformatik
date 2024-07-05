package com.example.projectworkmap.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "text_table")
data class Text(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @ColumnInfo(name = "avatar")
    val avatar: String,
    @ColumnInfo(name = "text")
    val text: String
)
