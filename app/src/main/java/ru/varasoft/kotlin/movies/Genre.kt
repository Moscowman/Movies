package ru.varasoft.kotlin.movies

sealed class Genre {
    class Horror : Genre()
    class Triller : Genre()
    class Comedy : Genre()
    class Drama : Genre()
    class Tragedy : Genre()
    class Action : Genre()
}
