package com.tuongvi.movieexplorer.di

import android.content.Context
import androidx.room.Room
import com.tuongvi.movieexplorer.data.local.AppDatabase
import com.tuongvi.movieexplorer.data.local.dao.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie_explorer.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieDao(database: AppDatabase): FavoriteMovieDao {
        return database.getFavoriteMovieDao()
    }
}
