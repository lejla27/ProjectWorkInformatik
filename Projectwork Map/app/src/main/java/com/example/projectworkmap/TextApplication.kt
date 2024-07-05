package com.example.projectworkmap

import android.app.Application
import com.example.projectworkmap.data.TextDatabase

class TextApplication: Application() {
    val database: TextDatabase by lazy { TextDatabase.getDatabase(this) }
}