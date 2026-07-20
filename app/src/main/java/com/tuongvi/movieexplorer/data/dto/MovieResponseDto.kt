package com.tuongvi.movieexplorer.data.dto

import androidx.compose.foundation.interaction.PressInteraction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("vote_average")
    val average: Double?,
    @SerialName("release_date")
    val releaseDate: String?
)

@Serializable
data class MovieResponseDto(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<MovieDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)