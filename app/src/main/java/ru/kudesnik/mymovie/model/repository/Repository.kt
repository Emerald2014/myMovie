package ru.kudesnik.mymovie.model.repository

import ru.kudesnik.mymovie.model.entities.Movie

interface Repository {
    fun getMoviesFromServer(): Movie
    fun getMoviesFromLocalStorage(): Movie
}