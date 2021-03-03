package ru.varasoft.kotlin.movies.model

sealed class Gender {
    class Man : Gender()
    class Woman : Gender()
}

