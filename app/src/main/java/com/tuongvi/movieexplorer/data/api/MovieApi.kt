package com.tuongvi.movieexplorer.data.api

import androidx.compose.ui.input.key.Key
import com.tuongvi.movieexplorer.data.dto.MovieDto
import com.tuongvi.movieexplorer.data.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    //endpoint popular: https://api.themoviedb.org/3/movie/popular?api_key=YOUR_KEY&language=vi-VN&page=1
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") laguage: String = "vi-VN",
        @Query("page") page: Int = 1
    ): MovieResponseDto

    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") laguage: String = "vi-VN",
        @Query("page") page: Int = 1
    ): MovieResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDto

}