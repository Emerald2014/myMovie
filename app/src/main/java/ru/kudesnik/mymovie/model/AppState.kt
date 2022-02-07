package ru.kudesnik.mymovie.model

import ru.kudesnik.mymovie.model.entities.Movie
import ru.kudesnik.mymovie.model.entities.MovieCategory

sealed class AppState {
    data class Success(val movieData: List<Movie>) : AppState()
    data class SuccessMovie(val movie: Movie)
    data class SuccessCat(val movieData: List<MovieCategory>) : AppState()

    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

