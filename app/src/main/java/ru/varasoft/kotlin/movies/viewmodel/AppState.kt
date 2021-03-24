package ru.varasoft.kotlin.movies.viewmodel

import ru.varasoft.kotlin.movies.model.MovieDTO

sealed class AppState {
    data class Success(val movieData: List<MovieDTO>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}