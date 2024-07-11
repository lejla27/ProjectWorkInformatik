package com.example.projectworkmap.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectworkmap.TextApplication
import com.example.projectworkmap.data.Text
import com.example.projectworkmap.data.TextDao
import com.example.projectworkmap.data.TextDatabase
import com.example.projectworkmap.data.TextRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class TextViewModel(private val textDao: TextDao) : ViewModel() {

    fun getFullText(): Flow<List<Text>> = textDao.getAll()
    fun getTextFor(cityName: String): Flow<List<Text>> {
        return textDao.getByCityName(cityName)
    }


    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TextApplication)
                TextViewModel(application.database.textDao())
            }
        }
    }
}

