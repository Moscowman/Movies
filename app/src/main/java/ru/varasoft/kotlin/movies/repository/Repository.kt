package ru.varasoft.kotlin.movies.repository

import ru.varasoft.kotlin.movies.model.MovieDTO

interface Repository{
    fun getMoviesFromServer(): List<MovieDTO>
    fun getMoviesFromLocalStorage(): List<MovieDTO>
}
