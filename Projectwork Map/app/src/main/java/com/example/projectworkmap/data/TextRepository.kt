package com.example.projectworkmap.data

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class TextRepository(private val textDao: TextDao) {

    val allTexts: Flow<List<Text>> = textDao.getAll()

    fun getByCityName(cityName: String): Flow<List<Text>> {
        return textDao.getByCityName(cityName)
    }

}