package com.tuongvi.movieexplorer.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tuongvi.movieexplorer.model.MovieListUiState
import com.tuongvi.movieexplorer.ui.components.MovieCard
import com.tuongvi.movieexplorer.viewmodel.MovieListViewModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.tuongvi.movieexplorer.model.Movie
import com.tuongvi.movieexplorer.viewmodel.MovieFavoriteListViewModel


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MovieFavoriteListScreen(
    favoriteMovies: List<Movie>,
    favoriteCount: Int,
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit,
    onMovieClick: (Int) -> Unit
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Movie Explorer",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                actions = {
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = onToggleDarkMode,
                        modifier = Modifier.padding(8.dp)
                    )
                    BadgedBox(
                        badge = {
                            if (favoriteCount > 0) {
                                Badge { Text("$favoriteCount") }
                            }
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Danh sách yêu thích",
                            tint = Color.Red
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()

            ){
                if (favoriteCount > 0) {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            top = 0.dp,
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = favoriteMovies,
                            key = { movie -> movie.id }
                        ) { movie ->
                            MovieCard(movie, onClick = {onMovieClick(movie.id)})
                        }
                    }
                } else{
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Chưa có phim yêu thích", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }

    }

}

@Composable
fun MovieFavoriteListRoute(
    onMovieClick: (Int) -> Unit,
    viewModel: MovieFavoriteListViewModel = hiltViewModel(),
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
){
    val favoriteMovies by viewModel.favoriteMovies.collectAsStateWithLifecycle()
    val favoriteCount by viewModel.favoriteCount.collectAsStateWithLifecycle()
    MovieFavoriteListScreen(
        favoriteMovies = favoriteMovies,
        favoriteCount = favoriteCount,
        isDarkMode = isDarkMode,
        onToggleDarkMode = onToggleDarkMode,
        onMovieClick = onMovieClick
    )
}