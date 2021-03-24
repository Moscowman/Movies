package ru.varasoft.kotlin.movies.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDTO (
    val genre_ids: Array<Int>,
    val id: Int?,
    val original_language: String?,
    val original_title: String? = "",
    val overview: String = "",
    val popularity: Float?,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = "",
    val video: Boolean,
    val vote_average: Float? = 0F,
    val vote_count: Int? = 0
) : Parcelable