package ru.kudesnik.mymovie.model.entities

data class Person(
    val id: Int,
    val enProfession: String = "director",
    val name: String,
    val birthPlace: String,
    val birthday: String
)
