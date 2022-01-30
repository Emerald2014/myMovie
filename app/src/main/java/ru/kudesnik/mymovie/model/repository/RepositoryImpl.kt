package ru.kudesnik.mymovie.model.repository

import ru.kudesnik.mymovie.model.entities.*
import ru.kudesnik.mymovie.utils.NetworkUtils

class RepositoryImpl : Repository {
    override fun getMoviesFromServer(id: Int): Movie {
        val dto = NetworkUtils.loadMovie(id)
        return Movie(
            name = dto?.name ?: "пусто",
            rating = dto?.rating?.kp ?: 0.0f,
            year = dto?.year ?: 1900,
            director = dto?.persons?.get(0)?.name ?: "Режиссер",
            category = dto?.genres?.get(0)?.name ?: "Комедия"

        )
    }

    override fun getMovieListFromServer(genres: String): List<Movie> {
        val dto = NetworkUtils.loadMovieList(genres)
        val movieList = mutableListOf<Movie>()

        if (dto != null) {
            for (index in dto.docs.indices) {

                movieList.add(
                    Movie(
                        id = dto.docs.get(index).id ?: 0,
                        name = dto.docs.get(index).name ?: "пусто",
                        rating = dto.docs.get(index).rating.kp ?: 0.0f,
                        year = dto.docs.get(index).year ?: 1900,
                    )
                )
            }
        }
        return movieList
    }

    override fun getMoviesFromLocalStorageComedy() = getMoviesCategoryComedy()
    override fun getMoviesFromLocalStorageAction() = getMoviesCategoryAction()
    override fun getMoviesFromLocalStorageFantastic() = getMoviesCategoryFantastic()
    override fun getMoviesFromLocalStorageMult() = getMoviesCategoryMult()


    override fun getMovieCategoryFromLocalStorage() = getMovieCategory()
}