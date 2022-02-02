package ru.kudesnik.mymovie.model.repository

import android.util.Log
import ru.kudesnik.mymovie.model.entities.*
import ru.kudesnik.mymovie.model.entities.rest.MovieRepo
import ru.kudesnik.mymovie.utils.NetworkUtils

class RepositoryImpl : Repository {
    override fun getMoviesFromServer(id: Int): Movie {
//        val dto = NetworkUtils.loadMovie(id)
        val dto =
            MovieRepo.api.getMovie(id, NetworkUtils.FIELD_ID, NetworkUtils.TOKEN).execute().body()
//        Log.i("MyResult2", MovieRepo.api)
        return Movie(
            name = dto?.name ?: "пусто",
            rating = dto?.rating?.kp ?: 0.0f,
            year = dto?.year ?: 1900,
            director = dto?.persons?.get(0)?.name ?: "Режиссер",
            category = dto?.genres?.get(0)?.name ?: "Комедия"

        )
    }

    override fun getMovieListFromServer(genres: String): List<Movie> {
        val dto = MovieRepo.apiList.getMovieList(genresName = genres, token = NetworkUtils.TOKEN)
            .execute().body()
//        val dto = NetworkUtils.loadMovieList(genres)
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