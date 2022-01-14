package ru.kudesnik.mymovie.model.repository

import ru.kudesnik.mymovie.model.entities.Movie
import ru.kudesnik.mymovie.model.entities.getMoviesCategoryAction
import ru.kudesnik.mymovie.model.entities.getMoviesCategoryComedy

class RepositoryImpl : Repository {
    override fun getMoviesFromServer() = Movie()

    override fun getMoviesFromLocalStorageComedy() = getMoviesCategoryComedy()
    override fun getMoviesFromLocalStorageAction() = getMoviesCategoryAction()
}