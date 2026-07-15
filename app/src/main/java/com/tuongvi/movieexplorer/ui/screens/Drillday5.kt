package com.tuongvi.movieexplorer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.navArgument


enum class Screen(val route: String){
    Home("home"),
    Profile("profile"),
    Setting("setting")
}

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(
            Screen.Home.route
        ){
            HomeScreen(onNavigateToProfile = {
                name ->
                val finalName = if (name.isEmpty()) "default" else name
                navController.navigate("${Screen.Profile.route}/$finalName")
            })
        }

        composable(
            "${Screen.Profile.route}/{name}",
            arguments = listOf(
                navArgument("name"){type = NavType.StringType}
            )
        ){ backStackEntry ->
            val userName = backStackEntry.arguments?.getString("name") ?: "..."
            ProfileScreen(
                userName = userName,
                onNavigateToSettings = {
                    navController.navigate(Screen.Setting.route)
                }
            )
        }

        composable(Screen.Setting.route){
            SettingsScreen()
        }
    }
}


@Composable
fun HomeScreen(onNavigateToProfile: (String) -> Unit) {
    var nameInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Đây là màn hình HOME", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = nameInput,
            onValueChange = {nameInput = it},
            label = {Text("Nhập tên")}
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onNavigateToProfile(nameInput) }) {
            Text("Đi tới Profile")
        }
    }
}

@Composable
fun ProfileScreen(userName: String, onNavigateToSettings: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Đây là màn hình PROFILE", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Xin chào: $userName", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToSettings) {
            Text("Đi tới Settings")
        }
    }
}

@Composable
fun SettingsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Đây là màn hình SETTINGS", style = MaterialTheme.typography.headlineMedium)
    }
}