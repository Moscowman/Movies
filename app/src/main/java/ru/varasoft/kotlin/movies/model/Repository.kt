package ru.varasoft.kotlin.movies.model

interface Repository{
    fun getMovieFromServer(): Movie
    fun getMovieFromLocalStorage(): List<Movie>
}
