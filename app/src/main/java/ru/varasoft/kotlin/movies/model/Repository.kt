package ru.varasoft.kotlin.movies.model

interface Repository{
    fun getMoviesFromServer(): List<MovieInListDTO>
    fun getMoviesFromLocalStorage(): List<MovieInListDTO>
}
