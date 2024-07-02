package com.example.projectworkmap.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room

@Database(entities = [Route::class], version = 1, exportSchema = false)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun routeDao(): RouteDao
    companion object {
        @Volatile
        private var Instance: QuizDatabase? = null
        fun getDatabase(context: Context): QuizDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, QuizDatabase::class.java, "item_database")
                    .build().also { Instance = it }
            }
        }
    }
}