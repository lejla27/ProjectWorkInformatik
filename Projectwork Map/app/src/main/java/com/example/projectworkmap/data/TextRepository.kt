package com.example.projectworkmap.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class TextRepository(private val textDao: TextDao) {

    val allTexts: LiveData<List<Text>> = textDao.getAll()

    fun getByCityName(cityName: String): LiveData<List<Text>> {
        return textDao.getByCityName(cityName)
    }
    @WorkerThread
    suspend fun insert(text: Text) {
        textDao.insertAll(text)
    }
}