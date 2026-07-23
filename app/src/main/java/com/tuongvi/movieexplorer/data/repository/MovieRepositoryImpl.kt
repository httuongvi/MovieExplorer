package com.tuongvi.movieexplorer.data.repository

import com.tuongvi.movieexplorer.data.api.MovieApi
import com.tuongvi.movieexplorer.data.api.RetrofitClient
import com.tuongvi.movieexplorer.data.dto.toMovie
import com.tuongvi.movieexplorer.model.Movie

class MovieRepositoryImpl(
    private val movieApi: MovieApi
): MovieRepository{
    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return runCatching {
            val response = movieApi.getPopularMovies(
                apiKey = RetrofitClient.API_KEY,
                page = page
            )

            response.results?.map { it.toMovie() } ?: emptyList()
        }
    }

    override suspend fun getSearchMovies(query: String, page: Int): Result<List<Movie>> {
        return  runCatching {
            val response = movieApi.getSearchMovies(
                apiKey = RetrofitClient.API_KEY,
                query = query,
                page = 1
            )
             response.results?.map{it.toMovie()} ?: emptyList()
        }
    }
}