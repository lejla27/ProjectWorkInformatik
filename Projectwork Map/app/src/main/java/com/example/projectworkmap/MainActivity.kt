package com.example.projectworkmap

import android.content.Context
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import com.example.projectworkmap.ui.theme.TextViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val textViewModel: TextViewModel by viewModels {
            TextViewModel.factory
        } // Pass the factory explicitly
        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController, textViewModel = textViewModel)
        }
    }
}



@Composable
fun NavGraph(navController: NavHostController, textViewModel: TextViewModel) {
    val context = navController.context
    var selectedAvatar by remember { mutableStateOf(getSavedAvatar(context)) }

    NavHost(navController, startDestination = "start_screen") {
        composable("start_screen") {
            StartPageWithButtonAndImage(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center), navController
            )
        }
        composable("map_screen") {
            MapWithButtonAndImage(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center), navController, selectedAvatar
            )
        }
        composable("avatar_screen") {
            AvatarSelectionScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center), navController
            ){
                selectedAvatar = it
                saveAvatar(context, it)
            }
        }
        composable("route_screen") {
            RouteSelectionScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center), navController, selectedAvatar
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
                painter = painterResource(id = imageResource),
                navController = navController,
                selectedAvatar = selectedAvatar
            )
        }

        composable(
            route = "introScreen/{cityName}",
            arguments = listOf(
            navArgument("cityName") { type = NavType.StringType }
            )
        )
        { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
            Intro(cityName = cityName, textViewModel = textViewModel)
        }
    }
}


@Composable
fun MapWithButtonAndImage(modifier: Modifier = Modifier, navController: NavHostController, selectedAvatar: String?) {
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
                onClick = { navController.navigate("avatar_screen") },
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
            LazyColumn {
                items(avatars) { avatar ->
                    AvatarListItem(avatar) {
                        selectedAvatar = avatar
                        onAvatarSelected(avatar)
                    }
                }
            }
            selectedAvatar.takeIf { it != "Select Avatar" }?.let {
                Image(
                    painter = painterResource(id = avatarImages[it] ?: 0),
                    contentDescription = "$it Image",
                    modifier = Modifier.size(250.dp)
                )
            }

            Button(
                onClick = { navController.navigate("route_screen") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)), // Dark red color
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
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = avatar, fontSize = 16.sp)
    }
}

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
                    text = "Munich to Heilbronn",
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


@Composable
fun CityScreen(
    cityName: String,
    painter: Painter,
    navController: NavController,
    selectedAvatar: String?,
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
                    navController.navigate("introScreen/$cityName")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
            ) {
                Text(text = "Explore")
            }
        }
        selectedAvatar?.let {
            val avatarResId = getAvatarResourceId(it)
            if (avatarResId != 0) {
                Image(
                    painter = painterResource(id = avatarResId),
                    contentDescription = "Selected Avatar",
                    modifier = Modifier
                        .size(600.dp)
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun Intro(cityName: String , textViewModel: TextViewModel) {
    //val textList by textViewModel.getTextFor(cityName).collectAsState(initial = emptyList())
    Text(
        text = "What do you know about $cityName?",
        fontSize = 50.sp,
        lineHeight = 50.sp,
        textAlign = TextAlign.Center
    )
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
