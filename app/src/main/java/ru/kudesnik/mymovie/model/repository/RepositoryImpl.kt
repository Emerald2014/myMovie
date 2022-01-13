package ru.kudesnik.mymovie.model.repository

import ru.kudesnik.mymovie.model.entities.Movie

class RepositoryImpl : Repository {
    override fun getMoviesFromServer() = Movie()

    override fun getMoviesFromLocalStorage() = Movie()
}