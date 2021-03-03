package ru.varasoft.kotlin.movies.model

data class Movie (val originalName : String, val russianName: String, val directors: Array<Person>, val actors: Array<Person>, val screenWriter: String, val year: Int, val genre: Array<Genre>, val rating: Float)