package com.tuongvi.movieexplorer
import com.tuongvi.movieexplorer.model.Movie
sealed interface MovieListUiState {
    object Loading : MovieListUiState
    data class Success(val movies: List<Movie>) : MovieListUiState
    data class Error (val message: String) : MovieListUiState
}