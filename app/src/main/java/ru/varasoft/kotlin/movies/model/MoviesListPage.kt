package ru.varasoft.kotlin.movies.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesListPage(
    val page: Int,
    val results: Array<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
) : Parcelable
