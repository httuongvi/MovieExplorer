package com.tuongvi.movieexplorer.data.repository

import com.tuongvi.movieexplorer.data.api.MovieApi
import com.tuongvi.movieexplorer.data.api.RetrofitClient
import com.tuongvi.movieexplorer.data.local.dao.FavoriteMovieDao
import com.tuongvi.movieexplorer.data.mapper.toFavoriteEntity
import com.tuongvi.movieexplorer.data.mapper.toMovie
import com.tuongvi.movieexplorer.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val favoriteMovieDao: FavoriteMovieDao
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

    override fun getAllFavoriteMovies(): Flow<List<Movie>> {
        return favoriteMovieDao.getAllFavoriteMovies().map { entities ->
            entities.map { it.toMovie() }
        }
    }
    override fun isFavorite(movieId: Int): Flow<Boolean> {
        return favoriteMovieDao.isFavorite(movieId)
    }

    override suspend fun addFavorite(movie: Movie) {
        favoriteMovieDao.insertFavorite(movie.toFavoriteEntity())
    }

    override suspend fun removeFavorite(movieId: Int) {
        favoriteMovieDao.deleteFavoriteMovieById(movieId)
    }

    override suspend fun getMovieDetail(movieId: Int): Result<Movie> {
        return try {
            val localMovie = favoriteMovieDao.getFavoriteMovieDetail(movieId)
            if(localMovie != null){
                Result.success(localMovie.toMovie())
            } else {
                val networkMovie = movieApi.getMovieDetail(movieId, RetrofitClient.API_KEY)
                Result.success(networkMovie
                    .toMovie())
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}