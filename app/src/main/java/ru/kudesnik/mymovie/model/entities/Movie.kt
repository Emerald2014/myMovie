package ru.kudesnik.mymovie.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int = 0,
    val name: String = "Аватар",
    val rating: Float = 7.9f,
    val category: String = "Фантастика",
    val director: String = "Джеймс Кемерон",
    val year: Int = 2000,
    val poster: String = "Будет позже",
    val movieLength: Int = 0,
    var comment: String = "Комментарий",
    val birthPlace: String = "USA",
    val birthdayDirector: String = "birthday",
    val directorId: Int = 31245
) : Parcelable {
}

fun getMoviesCategoryComedy() = listOf(
    Movie(0, "Комедия 1", 8.1f, "Комедия", "Режиссер 1", 2001),
    Movie(
        0,
        "Это очень длинное название фильма, чтобы оно не уместилось на экране",
        8.1f,
        "Комедия",
        "Режиссер 1",
        2001
    ),
    Movie(0, "Комедия 3", 8.1f, "Комедия", "Режиссер 1", 2001),
    Movie(326, "Комедия 4", 8.1f, "Комедия", "Режиссер 1", 2001),
)


fun getMoviesCategoryAction() = listOf(
    Movie(0, "Боевик 1", 8.1f, "Боевик", "Режиссер 1", 2001),
    Movie(0, "Боевик 2", 8.1f, "Боевик", "Режиссер 1", 2001),
    Movie(0, "Боевик 3", 8.1f, "Боевик", "Режиссер 1", 2001),
    Movie(0, "Боевик 4", 8.1f, "Боевик", "Режиссер 1", 2001),
)


fun getMoviesCategoryFantastic() = listOf(
    Movie(0, "Фантастика 1", 8.1f, MovieCategory.FANTASTIC.nameMovie, "Режиссер 1", 2001),
    Movie(0, "Фантастика 2", 8.1f, MovieCategory.FANTASTIC.nameMovie, "Режиссер 1", 2001),
    Movie(0, "Фантастика 3", 8.1f, MovieCategory.FANTASTIC.nameMovie, "Режиссер 1", 2001),
    Movie(0, "Фантастика 4", 8.1f, MovieCategory.FANTASTIC.nameMovie, "Режиссер 1", 2001),
)


fun getMoviesCategoryMult() = listOf(
    Movie(0, "Мультфильм 1", 8.1f, MovieCategory.MULT.nameMovie, "Режиссер 1", 2001),
//    Movie("Мультфильм 2", 8.1f, MovieCategory.MULT.nameMovie, "Режиссер 1", 2001),
//    Movie("Мультфильм 3", 8.1f, MovieCategory.MULT.nameMovie, "Режиссер 1", 2001),
//    Movie("Мультфильм 5", 6.2f, MovieCategory.MULT.nameMovie, "Режиссер 6", 1986),
//    Movie("Мультфильм 4", 8.1f, MovieCategory.MULT.nameMovie, "Режиссер 1", 2001),
)
