package com.example.projectworkmap.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface TextDao {
    @Query("SELECT * FROM text_table")
    fun getAll(): Flow<List<Text>>

    @Insert
    suspend fun insert(text: Text)

    @Query("SELECT * FROM text_table WHERE city_name = :cityName AND avatar = :avatar")
    fun getByCityNameAndAvatar(cityName: String, avatar: String): Flow<List<Text>>

}