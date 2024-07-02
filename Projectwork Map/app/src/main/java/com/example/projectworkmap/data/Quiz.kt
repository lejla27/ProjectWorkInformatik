package com.example.projectworkmap.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MunichToStuttgart")
data class Route(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityName: String,
    val quizQuestion: String,
    val quizAnswer: String
)