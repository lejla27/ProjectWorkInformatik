package com.example.projectworkmap

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun AvatarSelectionScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onAvatarSelected: (String) -> Unit) {
    var selectedAvatar by remember { mutableStateOf("Select Avatar") }
    val avatars = listOf("Elsa", "Barbie", "Spiderman", "Harry Potter")
    val avatarImages = mapOf(
        "Elsa" to R.drawable.elsa_drawable,
        "Barbie" to R.drawable.barbie_drawable,
        "Spiderman" to R.drawable.spiderman_drawable,
        "Harry Potter" to R.drawable.harrypotter_drawable
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.avatar_backgroundpicture),
            contentDescription = "Avatar Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose Avatar",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                selectedAvatar.takeIf { it != "Select Avatar" }?.let {
                    Image(
                        painter = painterResource(id = avatarImages[it] ?: 0),
                        contentDescription = "$it Image",
                        modifier = Modifier.size(250.dp)
                    )
                } ?: Text(
                    text = "No Avatar Selected",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
            LazyColumn {
                items(avatars) { avatar ->
                    AvatarListItem(avatar) {
                        selectedAvatar = avatar
                        onAvatarSelected(avatar)
                    }
                }
            }

            Button(
                onClick = { navController.navigate("route_screen") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF874F45)), // Dark red color
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Choose Route",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun AvatarListItem(avatar: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDCA79A)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = avatar, fontSize = 16.sp)
    }
}

// Utility functions to handle SharedPreferences
fun saveAvatar(context: Context, avatar: String) {
    val sharedPreferences = context.getSharedPreferences("avatar_pref", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("selected_avatar", avatar)
        apply()
    }
}

fun getSavedAvatar(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("avatar_pref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("selected_avatar", null)
}

@Composable
fun getAvatarResourceId(avatar: String): Int {
    return when (avatar) {
        "Elsa" -> R.drawable.elsa_drawable
        "Barbie" -> R.drawable.barbie_drawable
        "Spiderman" -> R.drawable.spiderman_drawable
        "Harry Potter" -> R.drawable.harrypotter_drawable
        else -> 0
    }
}