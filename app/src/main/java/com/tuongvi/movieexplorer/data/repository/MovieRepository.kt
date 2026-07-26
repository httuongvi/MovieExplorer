package com.tuongvi.movieexplorer.data.repository

import com.tuongvi.movieexplorer.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(page: Int = 1): Result<List<Movie>>
    suspend fun getSearchMovies(query: String, page: Int = 1): Result<List<Movie>>

    fun getAllFavoriteMovies(): Flow<List<Movie>>

    fun isFavorite(movieId: Int): Flow<Boolean>

    suspend fun addFavorite(movie: Movie)

    suspend fun removeFavorite(movieId: Int)

    suspend fun  getMovieDetail (movieId: Int): Result<Movie>
}