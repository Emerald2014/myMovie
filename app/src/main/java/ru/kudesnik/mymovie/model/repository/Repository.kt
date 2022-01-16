package ru.kudesnik.mymovie.model.repository

import ru.kudesnik.mymovie.model.entities.Movie
import ru.kudesnik.mymovie.model.entities.MovieCategory

interface Repository {
    fun getMoviesFromServer(): Movie
    fun getMoviesFromLocalStorageComedy(): List<Movie>
    fun getMoviesFromLocalStorageAction(): List<Movie>

    fun getMovieCategoryFromLocalStorage(): List<MovieCategory>
}