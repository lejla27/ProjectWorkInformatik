package com.example.projectworkmap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.projectworkmap.ui.theme.TextViewModel

@Composable
fun NavGraph(navController: NavHostController, textViewModel: TextViewModel, cities: List<String>) {
    val context = navController.context
    var selectedAvatar by remember { mutableStateOf(getSavedAvatar(context)) }
    var visitedCities by remember { mutableStateOf(setOf<String>()) }
    //val cities = cities
    var nextCityToVisit by remember { mutableStateOf(cities.first()) }

    NavHost(navController, startDestination = "start_screen") {
        composable("start_screen") {
            StartPageWithButtonAndImage(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center), navController
            )
        }
        composable("map_screen") {
            MapWithButtonAndImage(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                navController,
                selectedAvatar,
                visitedCities,
                nextCityToVisit,
                cities
            )
        }
        composable("avatar_screen") {
            AvatarSelectionScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center), navController
            ) {
                selectedAvatar = it
                saveAvatar(context, it)
            }
        }
        composable("route_screen") {
            RouteSelectionScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center), navController, selectedAvatar
            )
        }

        composable(
            route = "cityScreen/{cityName}/{imageResource}",
            arguments = listOf(
                navArgument("cityName") { type = NavType.StringType },
                navArgument("imageResource") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
            val imageResource = backStackEntry.arguments?.getInt("imageResource") ?: 0
            CityScreen(
                cityName = cityName,
                imageResource = imageResource,
                navController = navController,
                selectedAvatar = selectedAvatar
            )
        }

        composable(
            route = "introScreen/{cityName}/{imageResource}/{selectedAvatar}",
            arguments = listOf(
                navArgument("cityName") { type = NavType.StringType },
                navArgument("imageResource") { type = NavType.IntType },
                navArgument("selectedAvatar") { type = NavType.StringType }
            )
        )
        { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
            val imageResource = backStackEntry.arguments?.getInt("imageResource") ?: 0
            val selectedAvatar = backStackEntry.arguments?.getString("selectedAvatar") ?: ""
            Intro(
                cityName = cityName,
                imageResource = imageResource,
                avatar = selectedAvatar,
                viewModel = textViewModel
            ) {
                visitedCities = visitedCities + cityName
                val currentIndex = cities.indexOf(cityName)
                nextCityToVisit =
                    if (currentIndex + 1 < cities.size) cities[currentIndex + 1] else ""
                navController.navigate("map_screen")
            }
        }
    }
}