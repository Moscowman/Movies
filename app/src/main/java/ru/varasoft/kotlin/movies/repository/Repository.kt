package ru.varasoft.kotlin.movies.repository

import ru.varasoft.kotlin.movies.model.MovieInListDTO

interface Repository{
    fun getMoviesFromServer(): List<MovieInListDTO>
    fun getMoviesFromLocalStorage(): List<MovieInListDTO>
}
