package com.example.projectworkmap

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun RouteSelectionScreen(modifier: Modifier = Modifier, navController: NavHostController, selectedAvatar: String?) {
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
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ROUTE HISTORY",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = { navController.navigate("map_screen") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Munich to Stuttgart",
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = { /* Handle navigation */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Heilbronn to Stuttgart",
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = { /* Handle navigation */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Munich to Salzburg",
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = { /* Handle navigation */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Munich to Garching",
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = { /* Handle navigation */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Starnberg to Ulm",
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = { /* Handle navigation */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Augsburg to Munich",
                    fontSize = 16.sp
                )
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