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
                cities = listOf("Munich", "Augsburg",  "Ulm", "Stuttgart"),
                mainActivity = this
            )
        }
    }

    private fun initializeGraph() {
        graph = Graph()
        graph.addEdge("Munich", "Augsburg", 6)
        graph.addEdge("Augsburg", "Munich", 6)

        graph.addEdge("Munich", "Ingolstadt", 7)
        graph.addEdge("Ingolstadt", "Munich", 7)

        graph.addEdge("Augsburg", "Ulm", 7)
        graph.addEdge("Ulm", "Augsburg", 7)

        graph.addEdge("Augsburg", "Ingolstadt", 8)
        graph.addEdge("Ingolstadt", "Augsburg", 8)

        graph.addEdge("Nuremberg", "Ingolstadt", 9)
        graph.addEdge("Ingolstadt", "Nuremberg", 9)

        graph.addEdge("Regensburg", "Nuremberg", 5)
        graph.addEdge("Nuremberg", "Regensburg", 5)

        graph.addEdge("Ulm", "Nuremberg", 18)
        graph.addEdge("Nuremberg", "Ulm", 18)

        graph.addEdge("Heilbronn", "Nuremberg", 15)
        graph.addEdge("Nuremberg", "Heilbronn", 15)

        graph.addEdge("Regensburg", "Ingolstadt", 6)
        graph.addEdge("Ingolstadt", "Regensburg", 6)

        graph.addEdge("Ulm", "Stuttgart", 8)
        graph.addEdge("Stuttgart", "Ulm", 8)

        graph.addEdge("Munich", "Salzburg", 10)
        graph.addEdge("Salzburg", "Munich", 10)

        graph.addEdge("Heilbronn", "Stuttgart", 4)
        graph.addEdge("Stuttgart", "Heilbronn", 4)

    }

    public fun calculateRoute(fromCity: String, toCity: String): List<String> {
        val shortestPath = graph.findShortestPath(fromCity, toCity)
        val shortestPathList = LinkedList(shortestPath)
        println(shortestPathList)
        return shortestPathList
    }
}