package ru.kudesnik.mymovie.model.entities

data class Movie(
    val name: String,
    val rating: Float = 0f,
    val category: String,
    val director: String = "Режиссер",
    val year: Int,
    val poster: String = "Будет позже"
)
