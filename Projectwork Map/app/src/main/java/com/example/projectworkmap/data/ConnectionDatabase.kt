package com.example.projectworkmap.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Connection::class], version = 1, exportSchema = false)
abstract class ConnectionDatabase : RoomDatabase() {
    abstract fun connectionDao(): ConnectionDao

    companion object {
        @Volatile
        private var Instance: ConnectionDatabase? = null
        fun getDatabase(context: Context): ConnectionDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ConnectionDatabase::class.java, "connection_table")
                    .createFromAsset("database/connection_table.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{
                        Instance = it
                    }
            }
        }
    }
}