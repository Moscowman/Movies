package ru.varasoft.kotlin.movies.repository

import ru.varasoft.kotlin.movies.model.MovieDTO

interface LocalRepository {
    fun getAllHistory(): List<MovieDTO>
    fun saveEntity(movie: MovieDTO)
}