package com.example.projectworkmap.ui.theme

import android.content.Context
import java.io.File
import java.util.LinkedList

class RouteStorage(private val context: Context) {

    fun saveRouteToFile(route: List<String>, fileName: String) {
        val file = File(context.filesDir, fileName)
        file.writeText(route.joinToString(" -> "))
    }

    fun loadRouteFromFile(fileName: String): LinkedList<String>? {
        val file = File(context.filesDir, fileName)
        if (!file.exists()) return null

        val content = file.readText()
        val elements = content.split(" -> ")
        return LinkedList(elements)
    }
}