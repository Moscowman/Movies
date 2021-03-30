package ru.varasoft.kotlin.movies.utils

import ru.varasoft.kotlin.movies.model.MovieDTO

fun convertDtoToModel(movieDTO: MovieDTO): List<MovieDTO> {
    return listOf(
        movieDTO
    )
}
