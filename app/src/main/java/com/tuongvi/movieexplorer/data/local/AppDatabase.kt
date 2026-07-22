package com.tuongvi.movieexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tuongvi.movieexplorer.data.local.dao.FavoriteMovieDao
import com.tuongvi.movieexplorer.data.local.entity.FavoriteMovieEntity
import okhttp3.internal.connection.RouteDatabase

@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getFavoriteMovieDao(): FavoriteMovieDao
}