package ru.kudesnik.mymovie.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val name: String = "Аватар",
    val rating: Float = 7.9f,
    val category: String = "Фантастика",
    val director: String = "Джеймс Кемерон",
    val year: Int = 2000,
    val poster: String = "Будет позже"
) :Parcelable

fun getMoviesCategoryComedy(): List<Movie> {
    return listOf(
        Movie("Комедия 1", 8.1f, "Комедия", "Режиссер 1", 2001),
        Movie("Комедия 2", 8.1f, "Комедия", "Режиссер 1", 2001),
        Movie("Комедия 3", 8.1f, "Комедия", "Режиссер 1", 2001),
        Movie("Комедия 4", 8.1f, "Комедия", "Режиссер 1", 2001),
    )
}

fun getMoviesCategoryAction(): List<Movie> {
    return listOf(
        Movie("Боевик 1", 8.1f, "Боевик", "Режиссер 1", 2001),
        Movie("Боевик 2", 8.1f, "Боевик", "Режиссер 1", 2001),
        Movie("Боевик 3", 8.1f, "Боевик", "Режиссер 1", 2001),
        Movie("Боевик 4", 8.1f, "Боевик", "Режиссер 1", 2001),
    )
}