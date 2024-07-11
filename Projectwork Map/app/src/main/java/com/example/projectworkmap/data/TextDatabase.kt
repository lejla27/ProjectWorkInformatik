package com.example.projectworkmap.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room

@Database(entities = [Text::class], version = 1, exportSchema = false)
abstract class TextDatabase : RoomDatabase() {
    abstract fun textDao(): TextDao
    companion object {
        @Volatile
        private var Instance: TextDatabase? = null
        fun getDatabase(context: Context): TextDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TextDatabase::class.java, "text_table")
                    .createFromAsset("database/text_table.db") //.db datei muss genau so heißen!
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{
                        Instance = it
                    }
            }
        }
    }
}