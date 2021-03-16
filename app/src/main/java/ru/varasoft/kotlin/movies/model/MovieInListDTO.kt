package ru.varasoft.kotlin.movies.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieInListDTO (
    val id: Int?,
    val original_language: String?,
    val original_title: String? = "",
    val overview: String = "",
    val popularity: Float?,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = "",
    val genre: List<Genre>? = listOf(),
    val video: Boolean,
    val vote_average: Int? = 0,
    val vote_count: Int? = 0
) : Parcelable