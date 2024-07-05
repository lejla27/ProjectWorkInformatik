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
        private var INSTANCE: TextDatabase? = null
        fun getDatabase(context: Context): TextDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    TextDatabase::class.java,
                    "text_database"
                )
                    .createFromAsset("database/text.db") //database datei muss genau so heißen!
                    .build()
                    .also{
                        INSTANCE = it
                    }
            }
        }
    }
}