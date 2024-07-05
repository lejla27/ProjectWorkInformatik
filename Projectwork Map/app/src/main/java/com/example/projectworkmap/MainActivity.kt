package com.example.projectworkmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.projectworkmap.ui.theme.TextViewModel


class MainActivity : ComponentActivity() {
    val textViewModel: TextViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController, textViewModel = textViewModel)
        }
    }
}


@Composable
fun NavGraph(navController: NavHostController, textViewModel: TextViewModel) {
    NavHost(navController, startDestination = "start_screen") {
        composable("start_screen") {
            StartPageWithButtonAndImage(modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center), navController)
        }
        composable("map_screen") {
            MapWithButtonAndImage(modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center), navController)
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
                painter = painterResource(id = imageResource),
                navController = navController
            )
        }

        composable(
            route = "quizScreen/{cityName}",
            arguments = listOf(
            navArgument("cityName") { type = NavType.StringType }
            )
        )
        { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
            Quiz(cityName = cityName, textViewModel = textViewModel)
        }
    }
}


@Composable
fun MapWithButtonAndImage(modifier: Modifier = Modifier, navController: NavHostController) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.map_bild),
            contentDescription = "HintergrundBild",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
        ) {
            Button(
                onClick = { navController.navigate("cityScreen/Stuttgart/${R.drawable.stuttgart_bild}") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)), // Dark blue color
                modifier = Modifier.offset(x = 10.dp, y = (-210).dp)
            ) {
                Text(text = "Stuttgart")
            }

            Button(
                onClick = { navController.navigate("cityScreen/Ulm/${R.drawable.ulm_bild}") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                modifier = Modifier.offset(x = (-30).dp, y = (-70).dp)// Dark blue color
            ) {
                Text(text = "Ulm")
            }

            Button(
                onClick = { navController.navigate("cityScreen/Augsburg/${R.drawable.augsburg_bild}") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                modifier = Modifier.offset(x = 20.dp, y = 60.dp)// Dark blue color
            ) {
                Text(text = "Augsburg")
            }

            Button(
                onClick = { navController.navigate("cityScreen/Munich/${R.drawable.muenchen_bild}") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                modifier = Modifier.offset(x = 0.dp, y = 170.dp)// Dark blue color
            ) {
                Text(text = "Munich")
            }
        }
    }
}

@Composable
fun StartPageWithButtonAndImage(modifier: Modifier = Modifier, navController: NavHostController) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.startseiteimage),
            contentDescription = "HintergrundBild",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val buttonModifier = Modifier
                .fillMaxWidth(0.7f) // Adjust the width as needed to make all buttons the same size
                .height(48.dp)

            Button(
                onClick = { /* TODO: Handle Choose Avatar */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)), // Dark red color
                modifier = buttonModifier
            ) {
                Text(
                    text = "CHOOSE AVATAR",
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = { /* TODO: Handle Choose Route */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)), // Dark red color
                modifier = buttonModifier
            ) {
                Text(
                    text = "CHOOSE ROUTE",
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = {
                    navController.navigate("map_screen")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)), // Dark red color
                modifier = buttonModifier
            ) {
                Text(
                    text = "START",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun CityScreen(
    cityName: String,
    painter: Painter,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = "HintergrundBild",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column {
            Text(
                text = cityName,
                fontSize = 50.sp,
                lineHeight = 50.sp,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                    navController.navigate("quizScreen/$cityName")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
            ) {
                Text(text = "Explore")
            }
        }
    }
}

@Composable
fun Quiz(cityName: String, textViewModel: TextViewModel) {
    Text(
        text = "What do you know about $cityName ?",
        fontSize = 50.sp,
        lineHeight = 50.sp,
        textAlign = TextAlign.Center
        )
    val texts by textViewModel.getByCityName(cityName).observeAsState(emptyList())
    texts.forEach { text ->
        Text(
            text = text.text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
    }
    }