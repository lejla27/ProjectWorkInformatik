package com.example.projectworkmap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
    viewModel: TextViewModel = viewModel(factory = TextViewModel.factory),
    onFinish: () -> Unit // NEW
) {
    val texts by viewModel.getTextFor(cityName, avatar).collectAsState(initial = emptyList())

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
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "What do you know about $cityName?",
                fontSize = 50.sp,
                lineHeight = 50.sp,
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF8B0000)) // Dark red color for the box
                    .padding(16.dp)
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(texts) { text ->
                        Text(
                            text = text.text,
                            fontSize = 20.sp,
                            lineHeight = 28.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            Button(
                onClick = {
                    onFinish()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)) // Dark red color
            ) {
                Text(text = "Let's go on!")
            }
        }
    }
}