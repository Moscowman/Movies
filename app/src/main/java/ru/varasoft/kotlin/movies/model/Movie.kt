package ru.varasoft.kotlin.movies.model

data class Movie (
        val originalName : String = "",
        val russianName: String = "",
        val directors: MutableList<Person> = MutableList(0) {_ -> Person()},
        val actors: MutableList<Person> = MutableList(0) {_ -> Person()},
        val screenWriter: String = "",
        val year: Int = 1970,
        val genre: MutableList<Genre> = MutableList(0) {_ -> Genre.Horror() },
        val rating: Float = 0F
        )