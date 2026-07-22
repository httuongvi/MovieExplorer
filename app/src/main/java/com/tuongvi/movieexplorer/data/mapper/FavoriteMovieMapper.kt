package com.tuongvi.movieexplorer.data.mapper

import com.tuongvi.movieexplorer.data.local.entity.FavoriteMovieEntity
import com.tuongvi.movieexplorer.model.Movie

fun Movie.toFavoriteEntity(): FavoriteMovieEntity{
    return FavoriteMovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )
}

fun FavoriteMovieEntity.toMovie(): Movie{
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )
}