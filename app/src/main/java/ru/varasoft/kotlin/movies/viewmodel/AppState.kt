package ru.varasoft.kotlin.movies.viewmodel

import ru.varasoft.kotlin.movies.model.MovieInListDTO

sealed class AppState {
    data class Success(val movieData: List<MovieInListDTO>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}