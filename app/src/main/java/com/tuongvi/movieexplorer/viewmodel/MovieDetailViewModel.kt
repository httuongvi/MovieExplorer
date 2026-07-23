package com.tuongvi.movieexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuongvi.movieexplorer.data.repository.MovieRepository
import com.tuongvi.movieexplorer.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel(){
    private val _currentmovie = MutableStateFlow<Movie?>(null)
    val curentMovie: StateFlow<Movie?> = _currentmovie.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun setMovie(movie: Movie){
        _currentmovie.value = movie
        viewModelScope.launch {
            repository.isFavorite(movie.id).collect { favorite ->
                _isFavorite.value = favorite
            }
        }
    }

    fun toggleFavorite(){
        val movie = _currentmovie.value ?: return
        if (_isFavorite.value ){
            viewModelScope.launch {
                repository.removeFavorite(movie.id)
            }
        } else {
            viewModelScope.launch {
                repository.addFavorite(movie)
            }
        }
    }
}