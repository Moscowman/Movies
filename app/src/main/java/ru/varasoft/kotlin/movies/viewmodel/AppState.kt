package ru.varasoft.kotlin.movies.viewmodel

import ru.varasoft.kotlin.movies.model.Movie

sealed class AppState {
    data class Success(val movieData: List<Movie>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}