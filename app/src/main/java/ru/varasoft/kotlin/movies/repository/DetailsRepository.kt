package ru.varasoft.kotlin.movies.repository

import ru.varasoft.kotlin.movies.model.MovieDTO

interface DetailsRepository {
    fun getMovieDetailsFromServer(
        id: Int,
        callback: retrofit2.Callback<MovieDTO>
    )
}
