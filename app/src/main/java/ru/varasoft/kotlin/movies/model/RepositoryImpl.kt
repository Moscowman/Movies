package ru.varasoft.kotlin.movies.model

class RepositoryImpl : Repository {
    override fun getMovieFromServer(): Movie = Movie()

    override fun getMoviesFromLocalStorage(): List<Movie> = getMovies()
}