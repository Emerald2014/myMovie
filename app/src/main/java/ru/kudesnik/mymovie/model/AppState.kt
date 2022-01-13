package ru.kudesnik.mymovie.model

import ru.kudesnik.mymovie.model.entities.Movie

sealed class AppState {
    data class Success (val movieData: Movie) :AppState()
    data class Error(val error: Throwable): AppState()
    object Loading:AppState()
}

