package ru.kudesnik.mymovie.model.repository

import ru.kudesnik.mymovie.model.entities.*

class RepositoryImpl : Repository {
    override fun getMoviesFromServer() = Movie()

    override fun getMoviesFromLocalStorageComedy() = getMoviesCategoryComedy()
    override fun getMoviesFromLocalStorageAction() = getMoviesCategoryAction()
    override fun getMoviesFromLocalStorageFantastic() = getMoviesCategoryFantastic()
    override fun getMoviesFromLocalStorageMult() = getMoviesCategoryMult()


    override fun getMovieCategoryFromLocalStorage() = getMovieCategory()
}