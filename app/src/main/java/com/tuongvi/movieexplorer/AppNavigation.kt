package com.tuongvi.movieexplorer

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ){
        composable("list") {
            MovieListScreen(
                onMovieClick = { movieId ->
                    navController.navigate("detail/${movieId}")
                }
            )
        }

        composable(
            route = "detail/{movieId}",
            arguments = listOf(
                navArgument(name = "movieId"){type = NavType.IntType}
                )
            ){ backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailScreen(
                movieId,
                onBackClick = {
                    navController.popBackStack()
                }
            )

        }
    }
}