package ru.varasoft.kotlin.movies.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDTO (
    val id: Int?,
    val genre_ids: Array<Int> = arrayOf(),
    val original_language: String? = "en",
    val original_title: String? = "",
    val overview: String = "",
    val popularity: Float? = 0F,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = "",
    val video: Boolean = false,
    val vote_average: Float? = 0F,
    val vote_count: Int? = 0
) : Parcelable