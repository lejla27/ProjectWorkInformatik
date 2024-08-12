package com.example.projectworkmap.data

import androidx.compose.ui.unit.Dp
import androidx.room.Dao
import androidx.room.Query

@Dao
interface CityDao {

    @Query("SELECT image FROM city_table WHERE city IN (:cityName)")
    suspend fun getCityImage(cityName: String): String

    @Query("SELECT x_coordinate FROM city_table WHERE city IN (:cityName)")
    suspend fun getXcoordinate(cityName: String): Int

    @Query("SELECT y_coordinate FROM city_table WHERE city IN (:cityName)")
    suspend fun getYcoordinate(cityName: String): Int

    @Query("SELECT * FROM city_table")
    fun getAllCities(): List<City>

    @Query("SELECT city FROM city_table")
    suspend fun getCities(): List<String>

}