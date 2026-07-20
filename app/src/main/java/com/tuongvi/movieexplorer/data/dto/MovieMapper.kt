package com.tuongvi.movieexplorer.data.dto

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