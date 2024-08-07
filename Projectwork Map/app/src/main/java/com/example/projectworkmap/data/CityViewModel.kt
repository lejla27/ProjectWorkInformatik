package com.example.projectworkmap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projectworkmap.data.CityDatabase
import com.example.projectworkmap.data.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CityViewModel(application: Application) : AndroidViewModel(application) {
    private val db = CityDatabase.getDatabase(application)

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> = _cities

    init {
        fetchCities()
    }

    private fun fetchCities() {
        viewModelScope.launch(Dispatchers.IO) {
            _cities.postValue(db.cityDao().getAllCities())
        }
    }
}
