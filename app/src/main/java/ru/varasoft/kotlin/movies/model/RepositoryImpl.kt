package ru.varasoft.kotlin.movies.model

class RepositoryImpl : Repository {
    override fun getMovieFromServer(): Movie {
        return Movie()
    }
    override fun getMovieFromLocalStorage(): List<Movie> {
        return getMovies()
    }
}