package ru.kudesnik.mymovie.model.entities

data class Movie(
    val name: String = "Аватар",
    val rating: Float = 7.9f,
    val category: String = "Фантастика",
    val director: String = "Джеймс Кемерон",
    val year: Int = 2000,
    val poster: String = "Будет позже"
)
