package com.tuongvi.movieexplorer.data.api

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitClient{
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val  API_KEY = "f7237afff03cb242338fd8074cabb24f"
    private val json= Json{
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor (loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val movieApi: MovieApi by lazy {
        retrofit.create(MovieApi::class.java)
    }
}