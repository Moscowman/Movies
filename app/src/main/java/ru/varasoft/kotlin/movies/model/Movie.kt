package ru.varasoft.kotlin.movies.model

import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.*

@Parcelize
data class Movie(
        val originalName: String = "",
        val russianName: String = "",
        val directors: @RawValue MutableList<Person> = MutableList(0) { _ -> Person()},
        val actors: @RawValue MutableList<Person> = MutableList(0) { _ -> Person()},
        val screenWriters: @RawValue MutableList<Person> = MutableList(0) { _ -> Person()},
        val releaseDate: Date? = null,
        val budget: Long = 0,
        val revenue: Long = 0,
        val genre: @RawValue MutableList<Genre> = MutableList(0) { _ -> Genre.Horror() },
        val rating: Float = 0F,
        val usersRated: Int = 0,
        val length: Int = 0,
        val picture: @RawValue Image? = null,
        val like: Boolean = false,
        val plot: String = ""
        ) : Parcelable
