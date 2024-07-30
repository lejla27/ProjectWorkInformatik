package com.example.projectworkmap.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "connection_table")
data class Connection(
    @ColumnInfo(name = "fromCity")
    val fromCity: String,
    @ColumnInfo(name = "toCity")
    val toCity: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "weight")
    val weight: Int,
)
