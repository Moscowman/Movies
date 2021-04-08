package ru.varasoft.kotlin.movies.model

import java.util.*

data class Person (val surname : String = "", val name: String = "", val birthday: Date? = null, val gender: Gender? = null, val placeOfBirth: String? = null)
