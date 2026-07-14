package com.tuongvi.movieexplorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<MovieListUiState>(MovieListUiState.Loading)

    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }
    fun loadMovies(){
        viewModelScope.launch {
            _uiState.value = MovieListUiState.Loading

            delay(1500)

            try {
                _uiState.value = MovieListUiState.Success(movies = sampleMovies)
            } catch (
                e: Exception
            ){
                _uiState.value = MovieListUiState.Error("Lỗi tải phim")
            }
        }
    }

    fun refresh(){
        viewModelScope.launch {
            _uiState.value = MovieListUiState.Loading

            delay(1500)

            _uiState.value = MovieListUiState.Success(movies = sampleMovies)
        }
    }
}