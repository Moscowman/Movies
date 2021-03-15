package ru.varasoft.kotlin.movies.model

import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.text.DateFormat
import java.util.*

@Parcelize
data class Movie(
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
) : Parcelable

fun getMovies(): List<Movie> {
    return listOf(
        Movie(
            "Green mile", "Зелёная миля",
            mutableListOf(Person("Дарабонт", "Фрэнк"), Person("Вальдес", "Дэвид")),
            mutableListOf(Person("Дарабонт", "Фрэнк")),
            mutableListOf(
                Person("Хэнкс", "Том"),
                Person("Морс", "Дэвид"),
                Person("Хант", "Бонни"),
                Person("Дункан", "Майкл Кларк")
            ),
            mutableListOf(Person("Дарабонт", "Фрэнк"), Person("Кинг", "Стивен")),
            DateFormat.getDateInstance(DateFormat.SHORT).parse("01.01.1999"),
            60_000_000,
            290_701_374,
            mutableListOf(Genre.Drama, Genre.Fantasy),
            189
        ),
        Movie(
            "Titanic", "Титаник",
            mutableListOf(Person("Кэмерон", "Джеймс"), Person("Ландау", "Джон")),
            mutableListOf(Person("Кэмерон", "Джеймс")),
            mutableListOf(
                Person("Ди Каприо", "Леонардо"),
                Person("Уинслет", "Кейт"),
                Person("Зейн", "Билли"),
                Person("Пэкстон", "Билл")
            ),
            mutableListOf(Person("Кэмерон", "Джеймс")),
            DateFormat.getDateInstance(DateFormat.SHORT).parse("01.01.1999"),
            200_000_000,
            2_194_439_542,
            mutableListOf(Genre.Drama, Genre.Melodrama, Genre.Catastrophe),
            195
        ),
        Movie(
            "Avatar", "Аватар",
            mutableListOf(Person("Кэмерон", "Джеймс"), Person("Ландау", "Джон")),
            mutableListOf(Person("Кэмерон", "Джеймс")),
            mutableListOf(
                Person("Уортингтон", "Сэм"),
                Person("Салдана", "Зои"),
                Person("Уивер", "Сигурни"),
                Person("Лэнг", "Стивен"),
                Person("Родригес", "Мишель"),
                Person("Рибизи", "Джованни"),
                Person("Мур", "Джоэл Дэвид"),
            ),
            mutableListOf(Person("Кэмерон", "Джеймс")),
            DateFormat.getDateInstance(DateFormat.SHORT).parse("01.01.1999"),
            237_000_000,
            2_790_439_000,
            mutableListOf(Genre.Drama, Genre.Action, Genre.Adventure, Genre.ScienceFiction),
            162
        )
    )
}