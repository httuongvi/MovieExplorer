package com.tuongvi.movieexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuongvi.movieexplorer.data.repository.MovieRepository
import com.tuongvi.movieexplorer.model.Movie
import com.tuongvi.movieexplorer.model.MovieListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieFavoriteListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel(){
    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies.asStateFlow()

    private  val _favoriteCount = MutableStateFlow(0)
    val favoriteCount: StateFlow<Int> = _favoriteCount.asStateFlow()

    init {
        loadFavoriteMovies()
    }

    fun loadFavoriteMovies(){
        viewModelScope.launch {
            repository.getAllFavoriteMovies().collect { favoriteMovies ->
                _favoriteMovies.value = favoriteMovies
                _favoriteCount.value = favoriteMovies.size
            }
        }
    }

}