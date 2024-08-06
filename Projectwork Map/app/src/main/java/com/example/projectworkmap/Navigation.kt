package com.example.projectworkmap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.projectworkmap.ui.theme.RouteViewModel
import com.example.projectworkmap.ui.theme.TextViewModel


@Composable
fun NavGraph(
    navController: NavHostController,
    textViewModel: TextViewModel,
    mainActivity: MainActivity
) {
    val context = navController.context
    val routeViewModel: RouteViewModel = viewModel()

    var selectedAvatar by remember { mutableStateOf(getSavedAvatar(context)) }
    var visitedCities by remember { mutableStateOf(setOf<String>()) }
    val nextCityToVisit by routeViewModel.nextCity.observeAsState("")
    val shortestPath by routeViewModel.shortestPathList.observeAsState(emptyList())

    NavHost(navController, startDestination = "start_screen") {
        composable("start_screen") {
            StartPageWithButtonAndImage(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                navController = navController
            )
        }
        composable(route = "map_screen") {
            MapWithButtonAndImage(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                navController = navController,
                selectedAvatar = selectedAvatar,
                visitedCities = visitedCities,
                nextCityToVisit = nextCityToVisit,
                routeViewModel = routeViewModel
            )
        }
        composable("avatar_screen") {
            AvatarSelectionScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                navController = navController
            ) {
                selectedAvatar = it
                saveAvatar(context, it)
            }
        }
        composable("route_screen") {
            RouteSelectionScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                navController = navController,
                selectedAvatar = selectedAvatar,
                mainActivity = mainActivity,
                routeViewModel = routeViewModel
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
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
            val imageResource = backStackEntry.arguments?.getInt("imageResource") ?: 0
            val selectedAvatar = backStackEntry.arguments?.getString("selectedAvatar") ?: ""

            Intro(
                cityName = cityName,
                imageResource = imageResource,
                avatar = selectedAvatar,
                textViewModel = textViewModel,
                routeViewModel = routeViewModel,
                onFinish = {
                    visitedCities = visitedCities + cityName
                    val currentIndex = shortestPath.indexOf(cityName)
                    if (currentIndex + 1 < shortestPath.size) {
                        routeViewModel.setNextCityToVisit(shortestPath[currentIndex + 1])
                        navController.navigate("map_screen")
                    } else {
                        navController.navigate("end_screen/$cityName/$imageResource")
                    }
                }
            )
        }

        composable(
            route = "end_screen/{cityName}/{imageResource}",
            arguments = listOf(
                navArgument("cityName") { type = NavType.StringType },
                navArgument("imageResource") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
            val imageResource = backStackEntry.arguments?.getInt("imageResource") ?: 0
            EndPage(
                cityName = cityName,
                imageResource = imageResource,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                navController = navController
            )
        }
    }
}