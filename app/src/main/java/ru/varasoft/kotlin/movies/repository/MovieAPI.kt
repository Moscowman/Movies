package ru.varasoft.kotlin.movies.repository

import retrofit2.Call
import retrofit2.http.*
import ru.varasoft.kotlin.movies.model.MovieDTO

interface MovieAPI {
    @GET("3/movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieDTO>
}
