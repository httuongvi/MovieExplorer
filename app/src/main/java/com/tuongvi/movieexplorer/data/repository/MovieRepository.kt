package com.tuongvi.movieexplorer.data.repository

import com.tuongvi.movieexplorer.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(page: Int = 1): Result<List<Movie>>
    suspend fun getSearchMovies(query: String, page: Int = 1): Result<List<Movie>>
}