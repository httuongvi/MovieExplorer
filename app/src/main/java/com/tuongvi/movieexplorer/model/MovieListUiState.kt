package com.tuongvi.movieexplorer.model

sealed interface MovieListUiState {
    object Loading : MovieListUiState
    data class Success(val movies: List<Movie>) : MovieListUiState
    data class Error (val message: String) : MovieListUiState
}