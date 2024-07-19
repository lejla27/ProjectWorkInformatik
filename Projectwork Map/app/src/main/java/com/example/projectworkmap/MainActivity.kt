package com.example.projectworkmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.projectworkmap.ui.theme.TextViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val textViewModel: TextViewModel by viewModels {
            TextViewModel.factory
        } // Pass the factory explicitly
        val cities = listOf("Munich", "Augsburg", "Ulm", "Stuttgart") //images are not adaptable to the cities in the list (yet)
        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController, textViewModel = textViewModel, cities = cities)
        }
    }
}


