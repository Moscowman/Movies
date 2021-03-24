package ru.varasoft.kotlin.movies.repository

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory.*
import ru.varasoft.kotlin.movies.BuildConfig
import ru.varasoft.kotlin.movies.model.MovieDTO

class RemoteMovieDataSource {
    private val movieApi = Retrofit.Builder()
        .baseUrl("https://api.tmdb.org/")
        .addConverterFactory(
            create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(MovieAPI::class.java)

    fun getMovieDetails(movieId: Int, callback: Callback<MovieDTO>) {
        movieApi.getMovie(movieId, BuildConfig.THEMOVIEDB_API3_KEY).enqueue(callback)
    }
}