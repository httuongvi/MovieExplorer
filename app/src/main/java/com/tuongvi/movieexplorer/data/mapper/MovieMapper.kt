package com.tuongvi.movieexplorer.data.mapper

import com.tuongvi.movieexplorer.data.dto.MovieDto
import com.tuongvi.movieexplorer.model.Movie

fun MovieDto.toMovie(): Movie{
    return Movie(
        id = this.id,
        title = this.title ?: "Chưa có tiêu đề",
        overview = this.overview ?: "Chưa có mô tả",
        posterPath = this.posterPath,
        voteAverage = this.average ?: 0.0,
        releaseDate = this.releaseDate ?: "N/A"
    )
}