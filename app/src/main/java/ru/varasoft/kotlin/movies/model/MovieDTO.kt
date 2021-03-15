package ru.varasoft.kotlin.movies.model

import android.media.Image
import kotlinx.parcelize.RawValue
import java.util.*

data class MovieDTO (
    val originalName: String = "",
    val russianName: String = "",
    val producers: @RawValue MutableList<Person> = mutableListOf(),
    val directors: @RawValue MutableList<Person> = mutableListOf(),
    val actors: @RawValue MutableList<Person> = mutableListOf(),
    val screenWriters: @RawValue MutableList<Person> = mutableListOf(),
    val releaseDate: Date? = null,
    val budget: Long = 0,
    val revenue: Long = 0,
    val genre: @RawValue MutableList<Genre> = mutableListOf(),
    val length: Int = 0,
    val plot: String = "",
    val picture: @RawValue Image? = null,
    val like: Boolean = false,
    val rating: Float = 0F,
    val usersRated: Int = 0
)