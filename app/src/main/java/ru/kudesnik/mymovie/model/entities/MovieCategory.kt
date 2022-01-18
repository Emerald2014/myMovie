package ru.kudesnik.mymovie.model.entities

import android.os.Parcelable
import android.widget.Toast
import kotlinx.parcelize.Parcelize
import ru.kudesnik.mymovie.R

@Parcelize

enum class MovieCategory(val nameMovie: String, val icon: Int) : Parcelable {
    COMEDY("Комедия", R.drawable.ic_comedy),
    ACTION("Боевик", R.drawable.ic_action),
    FANTASTIC("Фантастика", R.drawable.ic_fantastic),
    MULT("Мультфильм", R.drawable.ic_mult),

}


fun getMovieCategoryString(): List<String> {

    return listOf(
        MovieCategory.COMEDY.nameMovie,
        MovieCategory.ACTION.nameMovie,
        MovieCategory.FANTASTIC.nameMovie,

        )
}


fun getMovieCategory(): List<MovieCategory> {
    return listOf(
        MovieCategory.COMEDY,
        MovieCategory.ACTION,
        MovieCategory.FANTASTIC,
        MovieCategory.MULT
    )
}
