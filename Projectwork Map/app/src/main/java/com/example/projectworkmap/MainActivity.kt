package com.example.projectworkmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.projectworkmap.data.ConnectionDao
import com.example.projectworkmap.ui.theme.Graph
import com.example.projectworkmap.ui.theme.RouteStorage
import com.example.projectworkmap.ui.theme.TextViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.LinkedList


class MainActivity : ComponentActivity() {

    private lateinit var graph: Graph
    private lateinit var routeStorage: RouteStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val textViewModel: TextViewModel by viewModels {
            TextViewModel.factory
        }

        routeStorage = RouteStorage(this)
        initializeGraph()

        setContent {
            val navController = rememberNavController()
            NavGraph(
                navController = navController,
                textViewModel = textViewModel,
                mainActivity = this
            )
        }
    }

    private fun initializeGraph() {

        graph = Graph()
        val connectionDao = (application as TextApplication).connectionDatabase.connectionDao()

        lifecycleScope.launch {
            val allConnections = withContext(Dispatchers.IO) {
                connectionDao.getAllConnections()
            }
            for (connection in allConnections) {
                graph.addEdge(connection.fromCity, connection.toCity, connection.weight)
            }
        }
    }

    fun calculateRoute(fromCity: String, toCity: String): List<String> {
        val shortestPath = graph.findShortestPath(fromCity, toCity)
        val shortestPathList = LinkedList(shortestPath)
        println("Shortest Path: $shortestPathList") //this print statement is only to test if the shortest path is correct
        return shortestPathList
    }
}