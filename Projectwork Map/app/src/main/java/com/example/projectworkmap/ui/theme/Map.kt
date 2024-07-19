package com.example.projectworkmap

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MapWithButtonAndImage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedAvatar: String?,
    visitedCities: Set<String>,
    nextCityToVisit: String,
    cities: List<String>
) {
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
        Column {
            cities.forEach { cityName -> // NEW: Iterate over the specific cities
                val imageResource = when (cityName) {
                    "Munich" -> R.drawable.muenchen_bild
                    "Augsburg" -> R.drawable.augsburg_bild
                    "Ulm" -> R.drawable.ulm_bild
                    "Stuttgart" -> R.drawable.stuttgart_bild
                    else -> 0
                }

                val modifier = when (cityName) { // NEW: Assign button positions
                    "Munich" -> Modifier.offset(x = 0.dp, y = 330.dp)
                    "Augsburg" -> Modifier.offset(x = 15.dp, y = 100.dp)
                    "Ulm" -> Modifier.offset(x = (-30).dp, y = (-140).dp)
                    "Stuttgart" -> Modifier.offset(x = 10.dp, y = (-360).dp)
                    else -> Modifier
                }

                if (imageResource != 0) { // Only create button if imageResource is valid
                    CityButton(
                        cityName = cityName,
                        navController = navController,
                        imageResource = imageResource,
                        isVisited = visitedCities.contains(cityName),
                        nextCityToVisit = nextCityToVisit,
                        modifier = modifier
                    )
                }
            }
        }
        selectedAvatar?.let {
            val avatarResId = getAvatarResourceId(it)
            if (avatarResId != 0) {
                Image(
                    painter = painterResource(id = avatarResId),
                    contentDescription = "Selected Avatar",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun CityButton(
    cityName: String,
    navController: NavHostController,
    imageResource: Int,
    isVisited: Boolean,
    nextCityToVisit: String,
    modifier: Modifier = Modifier, // NEW
    initialCity: Boolean = false
) {
    val buttonColor = when {
        isVisited -> Color.Gray // If the city has been visited, the button is gray
        cityName == nextCityToVisit -> Color(0xFF8B0000) // If the city is the next to visit, the button is red
        initialCity -> Color(0xFF8B0000) // Initially red for the first city
        else -> Color.Gray // Otherwise gray
    }
    Button(
        onClick = { navController.navigate("cityScreen/$cityName/$imageResource") },
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        modifier = modifier.padding(8.dp) // NEW
    ) {
        Text(text = cityName)
    }
}