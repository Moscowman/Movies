package ru.varasoft.kotlin.movies.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val movie_id: Int,
    val original_title: String? = "",
    val title: String? = ""
)
