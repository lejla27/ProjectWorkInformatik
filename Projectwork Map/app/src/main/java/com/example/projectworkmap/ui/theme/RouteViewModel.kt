package com.example.projectworkmap.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RouteViewModel : ViewModel() {
    val shortestPath = MutableLiveData<List<String>>()
    val shortestPathList: LiveData<List<String>> = shortestPath

    fun setShortestPath(path: List<String>) {
        shortestPath.value = path
    }
}