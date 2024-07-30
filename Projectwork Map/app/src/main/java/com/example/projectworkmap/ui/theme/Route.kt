package com.example.projectworkmap

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.projectworkmap.MainActivity
import com.example.projectworkmap.ui.theme.Graph
import com.example.projectworkmap.ui.theme.RouteStorage
import java.util.LinkedList

@Composable
fun RouteSelectionScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedAvatar: String?,
    mainActivity: MainActivity
) {
    var fromCity by remember { mutableStateOf<String?>(null) }
    var toCity by remember { mutableStateOf<String?>(null) }

    val citiesFrom = listOf("Munich", "Salzburg", "Ingolstadt", "Regensburg", "Nuremberg", "Heilbronn", "Stuttgart", "Augsburg", "Ulm")
    val citiesTo = citiesFrom

    val context = navController.context


    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.chooseroute_drawable),
            contentDescription = "HintergrundBild",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "FROM",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                citiesFrom.forEach { city ->
                    Button(
                        onClick = { fromCity = city },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (fromCity == city) Color(0xFF8B0000) else Color.Gray
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(text = city, fontSize = 16.sp)
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "TO",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                citiesTo.forEach { city ->
                    Button(
                        onClick = {
                            toCity = city
                            if (fromCity != null) {
                                val shortestPath = mainActivity.calculateRoute(fromCity!!, toCity!!)
                                val shortestPathString = shortestPath.joinToString(",")
                                navController.navigate("map_screen/$shortestPathString")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (toCity == city) Color(0xFF8B0000) else Color.Gray
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(text = city, fontSize = 16.sp)
                    }
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



