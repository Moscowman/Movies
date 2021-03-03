package ru.varasoft.kotlin.movies.model

import java.text.DateFormat
import java.util.*

data class Person (val surname : String = "", val name: String = "", val birthday: Date? = null, val gender: Gender? = null)
