package com.example.projectworkmap.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectworkmap.AppDatabase
import kotlinx.coroutines.flow.Flow


class TextViewModel(private val textDao: TextDao) : ViewModel() {

    fun getTextFor(cityName: String, avatar: String): Flow<List<Text>> {
        return textDao.getByCityNameAndAvatar(cityName, avatar)
    }


    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AppDatabase)
                TextViewModel(application.textDatabase.textDao())
            }
        }
    }
}

