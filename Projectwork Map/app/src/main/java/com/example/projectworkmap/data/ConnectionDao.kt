package com.example.projectworkmap.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConnectionDao {
        @Query("SELECT * FROM connection_table")
        suspend fun getAllConnections(): List<Connection>

        @Query("SELECT DISTINCT fromCity FROM connection_table")
        suspend fun getAllCities(): List<String>
}