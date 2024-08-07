package com.example.projectworkmap.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var Instance: CityDatabase? = null
        fun getDatabase(context: Context): CityDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CityDatabase::class.java, "city_table")
                    .createFromAsset("database/city_table.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{
                        Instance = it
                    }
            }
        }
    }
}