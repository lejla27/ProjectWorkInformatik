package com.example.projectworkmap.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RouteViewModel : ViewModel() {
    private val shortestPath = MutableLiveData<List<String>>()
    val shortestPathList: LiveData<List<String>> = shortestPath

    private val _nextCity = MutableLiveData<String>()
    val nextCity: LiveData<String> = _nextCity


    fun setShortestPath(path: List<String>) {
        shortestPath.value = path
        _nextCity.value = path.firstOrNull() ?: ""
    }

    fun setNextCityToVisit(city: String) {
        _nextCity.value = city
    }

    // Function to reset the route-related variables
    fun resetRoute() {
        shortestPath.value = emptyList() // Reset the shortest path
        _nextCity.value = "" // Clear the next city to visit
    }

}