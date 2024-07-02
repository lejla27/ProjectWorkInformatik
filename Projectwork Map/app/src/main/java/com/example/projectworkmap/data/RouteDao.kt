package com.example.projectworkmap.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Route)
    @Update
    suspend fun update(item: Route)
    @Delete
    suspend fun delete(item: Route)
    @Query("SELECT * from MunichToStuttgart WHERE id = :id")
    fun getQuestion(id: Int): Flow<Route>
    @Query("SELECT * from MunichToStuttgart ORDER BY cityName ASC")
    fun getAllItems(): Flow<List<Route>>
}

