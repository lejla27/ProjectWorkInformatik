package com.example.projectworkmap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projectworkmap.ui.theme.RouteViewModel
import com.example.projectworkmap.ui.theme.TextViewModel

@Composable
fun CityScreen(
    cityName: String,
    imageResource: Int,
    navController: NavController,
    selectedAvatar: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "HintergrundBild",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = cityName,
                fontSize = 50.sp,
                lineHeight = 50.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            selectedAvatar?.let {
                val avatarResId = getAvatarResourceId(it)
                if (avatarResId != 0) {
                    Image(
                        painter = painterResource(id = avatarResId),
                        contentDescription = "Selected Avatar",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate("introScreen/$cityName/$imageResource/$selectedAvatar")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)) // Dark red color
            ) {
                Text(text = "Explore")
            }
        }
    }
}

@Composable
fun Intro(
    cityName: String,
    imageResource: Int,
    avatar: String,
    textViewModel: TextViewModel = viewModel(factory = TextViewModel.factory),
    routeViewModel: RouteViewModel,
    onFinish: () -> Unit
) {
    val texts by textViewModel.getTextFor(cityName, avatar).collectAsState(initial = emptyList())

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "HintergrundBild",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 2.dp, color = Color.Yellow)
                    .background(Color(0xFF8B0000).copy(alpha = 0.5f)) // Dark red color for the box
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Text(
                        text = "What do you know about $cityName?",
                        fontSize = 40.sp,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(texts) { text ->
                            Text(
                                text = text.text,
                                fontSize = 15.sp,
                                lineHeight = 28.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter // Aligns the button at the bottom center
        ) {
            Button(
                onClick = { onFinish() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)), // Dark red color for the button
                modifier = Modifier.padding(bottom = 50.dp) // Add padding to move the button up from the bottom
            ) {
                Text(text = "Let's go on!")
            }
        }
    }
}