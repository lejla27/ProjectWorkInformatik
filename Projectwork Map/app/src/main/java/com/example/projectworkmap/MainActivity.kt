package com.example.projectworkmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.projectworkmap.ui.theme.Graph
import com.example.projectworkmap.ui.theme.RouteStorage
import com.example.projectworkmap.ui.theme.TextViewModel
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
                onCalculateRoute = ::calculateRoute,
                cities = listOf("Munich", "Heilbronn", "Stuttgart", "Augsburg", "Ulm", "Starnberg", "Salzburg", "Rosenheim", "Garching")
            )
        }
    }

    private fun initializeGraph() {
        graph = Graph()
        graph.addEdge("Munich", "Augsburg", 1)
        graph.addEdge("Augsburg", "Munich", 1)

        graph.addEdge("Augsburg", "Ulm", 1)
        graph.addEdge("Ulm", "Augsburg", 1)

        graph.addEdge("Ulm", "Stuttgart", 1)
        graph.addEdge("Stuttgart", "Ulm", 1)

        graph.addEdge("Munich", "Salzburg", 1)
        graph.addEdge("Salzburg", "Munich", 1)

        graph.addEdge("Munich", "Rosenheim", 1)
        graph.addEdge("Rosenheim", "Munich", 1)

        graph.addEdge("Rosenheim", "Salzburg", 1)
        graph.addEdge("Salzburg", "Rosenheim", 1)

        graph.addEdge("Starnberg", "Augsburg", 1)
        graph.addEdge("Augsburg", "Starnberg", 1)

        graph.addEdge("Starnberg", "Munich", 1)
        graph.addEdge("Munich", "Starnberg", 1)

        graph.addEdge("Heilbronn", "Stuttgart", 1)
        graph.addEdge("Stuttgart", "Heilbronn", 1)

        graph.addEdge("Garching", "Munich", 1)
        graph.addEdge("Munich", "Garching", 1)
    }

    private fun calculateRoute(fromCity: String, toCity: String) {
        val shortestPathList = graph.findShortestPath(fromCity, toCity)
        val shortestPath = LinkedList(shortestPathList)
        println("Shortest Path: $shortestPath")
        routeStorage.saveRouteToFile(shortestPath, "shortest_path.txt")
    }
}