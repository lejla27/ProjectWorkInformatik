package com.example.projectworkmap

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projectworkmap.ui.theme.RouteViewModel

@Composable
fun MapWithButtonAndImage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedAvatar: String?,
    visitedCities: Set<String>,
    nextCityToVisit: String,
    routeViewModel: RouteViewModel
) {
    val cities by routeViewModel.shortestPathList.observeAsState(initial = emptyList())

    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    // Define the size of the map image in dp
    val mapWidthDp = 1000.dp
    val mapHeightDp = 1000.dp

    // Conversion function from pixels to dp
    @Composable
    fun Int.pixelsToDp(): Dp {
        return (this / LocalContext.current.resources.displayMetrics.density).dp
    }



    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(verticalScrollState)
            .horizontalScroll(horizontalScrollState),
        contentAlignment = Alignment.Center
    ) {
        // Scrollable map layer
        Box(
            modifier = Modifier
                .width(mapWidthDp)
                .height(mapHeightDp)
        ) {
            Image(
                painter = painterResource(R.drawable.map_bild),
                contentDescription = "HintergrundBild",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Define positions in pixels on the map image
            val cityPositions = mapOf(
                "Munich" to Pair(1800, 1950),
                "Augsburg" to Pair(1450, 1750),
                "Ulm" to Pair(900, 1600),
                "Stuttgart" to Pair(300, 1300),
                "Heilbronn" to Pair(180, 900),
                "Nuremberg" to Pair(1550, 610),
                "Regensburg" to Pair(2250, 1050),
                "Ingolstadt" to Pair(1700, 1400),
                "Salzburg" to Pair(2400, 2000)
            )

            // Position buttons on the map using the pixel coordinates
            cities.forEach { cityName ->
                val imageResource = when (cityName) {
                    "Munich" -> R.drawable.muenchen_bild
                    "Augsburg" -> R.drawable.augsburg_bild
                    "Ulm" -> R.drawable.ulm_bild
                    "Stuttgart" -> R.drawable.stuttgart_bild
                    "Heilbronn" -> R.drawable.heilbronn_bild
                    "Nuremberg" -> R.drawable.nuremberg_bild
                    "Regensburg" -> R.drawable.regensburg_bild
                    "Ingolstadt" -> R.drawable.ingolstadt_bild
                    "Salzburg" -> R.drawable.salzburg_bild
                    else -> 0
                }

                cityPositions[cityName]?.let { (xPixel, yPixel) ->
                    val buttonModifier = Modifier.offset(
                        x = xPixel.pixelsToDp(),
                        y = yPixel.pixelsToDp()
                    )

                    if (imageResource != 0) {
                        CityButton(
                            cityName = cityName,
                            navController = navController,
                            imageResource = imageResource,
                            isVisited = visitedCities.contains(cityName),
                            nextCityToVisit = nextCityToVisit,
                            modifier = buttonModifier
                        )
                    }
                }
            }
        }

        // Selected avatar (if any)
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
    modifier: Modifier = Modifier,
    initialCity: Boolean = false
) {
    val isLocked = if (initialCity) false else !isVisited && cityName != nextCityToVisit
    val context = LocalContext.current

    val buttonColor = when {
        isVisited -> Color.Gray // If the city has been visited, the button is gray
        cityName == nextCityToVisit -> Color(0xFF8B0000) // If the city is the next to visit, the button is red
        initialCity -> Color(0xFF8B0000) // Initially red for the first city
        else -> Color.Gray // Otherwise gray
    }
    Button(
        onClick = {
            if (isLocked) {
                showToast(context, "You have not reached this city yet!")
            } else {
                navController.navigate("cityScreen/$cityName/$imageResource")
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        modifier = modifier.padding(8.dp)
    ) {
        Text(text = cityName)
    }
}

fun showToast(context: Context, message: String) {
    val inflater = LayoutInflater.from(context)
    val layout = inflater.inflate(R.layout.custom_toast, null)
    val textView: TextView = layout.findViewById(R.id.text)
    textView.text = message

    with(Toast(context)) {
        duration = Toast.LENGTH_SHORT
        view = layout
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}