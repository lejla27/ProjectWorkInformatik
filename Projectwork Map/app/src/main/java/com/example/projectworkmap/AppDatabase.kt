package com.example.projectworkmap

import android.app.Application
import com.example.projectworkmap.data.ConnectionDatabase
import com.example.projectworkmap.data.TextDatabase

class AppDatabase: Application() {
    val textDatabase: TextDatabase by lazy { TextDatabase.getDatabase(this) }
    val connectionDatabase: ConnectionDatabase by lazy { ConnectionDatabase.getDatabase(this) }
}