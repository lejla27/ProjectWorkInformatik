package com.example.startseite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.startseite.ui.theme.StartseiteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StartseiteTheme {
                ProjectWorkApp()
            }
        }
    }
}

@Composable
@Preview
fun ProjectWorkApp() {
    ProjectWorkWithButtonAndImage(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun ProjectWorkWithButtonAndImage(modifier: Modifier = Modifier) {
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
            Button(
                onClick = { /* TODO: Handle Choose Avatar */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00008B)) // Dark blue color
            ) {
                Text(text = "CHOOSE AVATAR")
            }
            Button(
                onClick = { /* TODO: Handle Choose Route */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00008B)) // Dark blue color
            ) {
                Text(text = "CHOOSE ROUTE")
            }
            Button(
                onClick = { /* TODO: Handle Start */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00008B)) // Dark blue color
            ) {
                Text(text = "START")
            }
        }
    }
}