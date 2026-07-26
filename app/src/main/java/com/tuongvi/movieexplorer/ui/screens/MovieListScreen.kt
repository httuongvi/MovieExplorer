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
import androidx.compose.material.icons.filled.Brightness5
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.tuongvi.movieexplorer.R


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MovieListScreen(
    uiState: MovieListUiState,
    searchQuery: String,
    favoriteCount: Int,
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit,
    onRefresh: () -> Unit,
    onRetry : () -> Unit,
    onMovieClick: (Int) -> Unit,
    onSearch: (String) -> Unit,
    onClearSearch: () -> Unit,
    onHeartClick: () -> Unit,
    onSettingClick: () -> Unit
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.app_name
                        ),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onRefresh) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Làm mới danh sách"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onSettingClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Brightness5,
                            contentDescription = "Setting"
                        )
                    }
                    BadgedBox(
                        badge = {
                            if (favoriteCount > 0) {
                                Badge { Text("$favoriteCount") }
                            }
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        IconButton(
                            onClick = onHeartClick
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Danh sách yêu thích",
                                tint = Color.Red
                            )
                        }
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
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { onSearch(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text(text = stringResource(
                    id = R.string.search_hint
                )) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = onClearSearch) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Xóa từ khóa")
                        }
                    }
                },
                singleLine = true
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()

            ){
                when (val state = uiState){
                    is MovieListUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    is MovieListUiState.Success -> {
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
                                items = state.movies,
                                key = { movie -> movie.id }
                            ) { movie ->
                                MovieCard(movie, onClick = {onMovieClick(movie.id)})
                            }
                        }
                    }
                    is MovieListUiState.Error ->{
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = state.message, color = MaterialTheme.colorScheme.error)
                            Button(onClick = onRetry) {
                                Text(text = stringResource(
                                    id = R.string.retry
                                ))
                            }
                        }
                    }
                }
            }
        }

    }

}

@Composable
fun MovieListRoute(
    onMovieClick: (Int) -> Unit,
    viewModel: MovieListViewModel = hiltViewModel(),
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit,
    onHeartClick: () -> Unit,
    onSettingClick: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val favoriteCount by viewModel.favoriteCount.collectAsStateWithLifecycle()
    MovieListScreen(
        uiState = uiState,
        searchQuery = searchQuery,
        favoriteCount = favoriteCount,
        isDarkMode = isDarkMode,
        onToggleDarkMode = onToggleDarkMode,
        onRefresh = { viewModel.refresh() },
        onRetry = { viewModel.refresh() },
        onMovieClick = onMovieClick,
        onSearch = {query: String -> viewModel.searchMovies(query)},
        onClearSearch = { viewModel.searchMovies("") },
        onHeartClick = onHeartClick,
        onSettingClick = onSettingClick
    )
}