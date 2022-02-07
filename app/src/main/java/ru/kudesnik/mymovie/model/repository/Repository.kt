package ru.kudesnik.mymovie.model.repository

import ru.kudesnik.mymovie.model.entities.Movie
import ru.kudesnik.mymovie.model.entities.MovieCategory

interface Repository {

    fun getMoviesFromServer(id: Int): Movie
    fun getMoviesFromLocalStorageComedy(): List<Movie>
    fun getMoviesFromLocalStorageAction(): List<Movie>
    fun getMoviesFromLocalStorageFantastic(): List<Movie>
    fun getMoviesFromLocalStorageMult(): List<Movie>

    fun getMovieCategoryFromLocalStorage(): List<MovieCategory>
    fun getMovieListFromServer(genres: String, isShortMovieLength: Boolean): List<Movie>

    fun saveEntity(movie: Movie)
    fun getAllFavourites(): List<Movie>

    fun saveHistoryEntity(movie: Movie)
    fun getAllHistory(): List<Movie>

    fun deleteMovie(movie: Movie)

    fun updateMovie(movie: Movie)
}