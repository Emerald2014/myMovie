package ru.kudesnik.mymovie.model.entities

import android.os.Parcelable
import android.widget.Toast
import kotlinx.parcelize.Parcelize
import ru.kudesnik.mymovie.R

@Parcelize
enum class MovieCategory(val nameMovie: String, val icon: Int) : Parcelable {
    COMEDY("комедия", R.drawable.ic_comedy),
    ACTION("боевик", R.drawable.ic_action),
    FANTASTIC("фантастика", R.drawable.ic_fantastic),
    MULT("Мультфильм", R.drawable.ic_mult),
}

fun getMovieCategory(): List<MovieCategory> {
    return listOf(
        MovieCategory.COMEDY,
        MovieCategory.ACTION,
        MovieCategory.FANTASTIC,
        MovieCategory.MULT
    )
}
