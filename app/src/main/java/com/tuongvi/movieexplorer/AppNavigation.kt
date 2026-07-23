package com.tuongvi.movieexplorer

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tuongvi.movieexplorer.ui.screens.MovieDetailScreen
import com.tuongvi.movieexplorer.ui.screens.MovieListRoute
import com.tuongvi.movieexplorer.ui.screens.MovieListScreen
import com.tuongvi.movieexplorer.viewmodel.MovieListViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    val movieViewModel: MovieListViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "list"
    ){
        composable("list") {
            MovieListRoute(
                viewModel = movieViewModel,
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
            val selectedMovie = movieViewModel.getMovieById(movieId)
            MovieDetailScreen(
                movie = selectedMovie,
                onBackClick = {
                    navController.popBackStack()
                }
            )

        }
    }
}