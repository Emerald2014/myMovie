package ru.kudesnik.mymovie.model.repository

import ru.kudesnik.mymovie.model.entities.*
import ru.kudesnik.mymovie.model.entities.database.Database
import ru.kudesnik.mymovie.model.entities.database.FavouriteEntity
import ru.kudesnik.mymovie.model.entities.database.HistoryDatabase
import ru.kudesnik.mymovie.model.entities.database.HistoryEntity
import ru.kudesnik.mymovie.model.entities.rest.MovieRepo
import ru.kudesnik.mymovie.model.entities.rest.rest_entities.PersonsKP
import ru.kudesnik.mymovie.utils.NetworkUtils

var shortMovieLength = ""

class RepositoryImpl() : Repository {
    override fun getMoviesFromServer(id: Int): Movie {
        val dto =
            MovieRepo.api.getMovie(id, NetworkUtils.FIELD_ID, NetworkUtils.TOKEN).execute().body()

        return Movie(
            id = dto?.id ?: 5,
            name = dto?.name ?: "пусто",
            rating = dto?.rating?.kp ?: 0.0f,
            year = dto?.year ?: 1900,
            director = dto?.persons?.get(0)?.name ?: "Режиссер",
            category = dto?.genres?.get(0)?.name ?: "Комедия",
            poster = dto?.poster?.url ?: "",
            directorId = dto?.persons?.get(0)?.id ?: 0
        )
    }

    override fun getMovieListFromServer(genres: String, isShortMovieLenght: Boolean): List<Movie> {
        when (isShortMovieLenght) {
            true -> shortMovieLength = "0-50"
            false -> shortMovieLength = "0-1000"
        }
        val dto = MovieRepo.apiList.getMovieList(
            genresName = genres,
            token = NetworkUtils.TOKEN,
            shortMovieLength = shortMovieLength
        )
            .execute().body()
        val movieList = mutableListOf<Movie>()

        if (dto != null) {
            for (index in dto.docs.indices) {

                movieList.add(
                    Movie(
                        id = dto.docs.get(index).id ?: 0,
                        name = dto.docs.get(index).name ?: "пусто",
                        rating = dto.docs.get(index).rating.kp ?: 0.0f,
                        year = dto.docs.get(index).year ?: 1900,
                        poster = dto.docs.get(index).poster.url ?: "",
                        movieLength = dto.docs.get(index).movieLength
                    )
                )
            }
        }
        return movieList
    }

    override fun getPersonFromServer(id: Int): Person {
        val dto =
            MovieRepo.apiPerson.getPerson(id, token = NetworkUtils.TOKEN).execute().body()
//        Log.i("MyResult2", MovieRepo.api)
        return Person(
            id = dto?.id ?: 5,
            name = dto?.name ?: "пусто",

            birthPlace = dto?.birthPlace?.get(0)?.value ?: "USA",
            birthday = dto?.birthday ?: "birthday"
        )
    }

    override fun getMoviesFromLocalStorageComedy() = getMoviesCategoryComedy()
    override fun getMoviesFromLocalStorageAction() = getMoviesCategoryAction()
    override fun getMoviesFromLocalStorageFantastic() = getMoviesCategoryFantastic()
    override fun getMoviesFromLocalStorageMult() = getMoviesCategoryMult()


    override fun getMovieCategoryFromLocalStorage() = getMovieCategory()

    //Favourites
    override fun getAllFavourites(): List<Movie> {
//        return convertFavouriteEntityToMovie(localDataSource.all())
        return convertFavouriteEntityToMovie(Database.db.favouriteDao().all())
    }

    override fun saveEntity(movie: Movie) {
//        localDataSource.insert(convertMovieToEntity(movie))
        Database.db.favouriteDao().insert(convertMovieToEntity(movie))
    }

    private fun convertFavouriteEntityToMovie(entityList: List<FavouriteEntity>): List<Movie> {
        return entityList.map {
            Movie(
                it.idEntity,
                it.nameMovieEntity,
                it.ratingMovieEntity,

                )
        }
    }

    private fun convertMovieToEntity(movie: Movie): FavouriteEntity {
        return FavouriteEntity(
            movie.id,
            movie.name,
            movie.movieLength,
            movie.poster,
            movie.rating
        )
    }

    //History
    override fun saveHistoryEntity(movie: Movie) {
        HistoryDatabase.db.historyDao().insert(convertMovieToHistoryEntity(movie))
    }

    override fun getAllHistory(): List<Movie> {
        return convertHistoryEntityToMovie(HistoryDatabase.db.historyDao().all())
    }

    private fun convertHistoryEntityToMovie(entityList: List<HistoryEntity>): List<Movie> {
        return entityList.map {
            Movie(
                id = it.idEntity,
                name = it.nameMovieEntity,
                movieLength = it.durationMovieEntity,
                poster = it.posterMovieEntity,
                rating = it.ratingMovieEntity,
                comment = it.commentMovieEntity
            )
        }
    }

    private fun convertMovieToHistoryEntity(movie: Movie): HistoryEntity {
        return HistoryEntity(
            idEntity = movie.id,
            nameMovieEntity = movie.name,
            durationMovieEntity = movie.movieLength,
            posterMovieEntity = movie.poster,
            ratingMovieEntity = movie.rating,
            commentMovieEntity = movie.comment
        )
    }

    override fun deleteMovie(movie: Movie) {
        HistoryDatabase.db.historyDao()
            .deleteByMovieName(convertMovieToHistoryEntity(movie).nameMovieEntity)
    }

    override fun updateMovie(movie: Movie) {
        HistoryDatabase.db.historyDao()
            .updateByMovieComment(
                convertMovieToHistoryEntity(movie).nameMovieEntity,
                convertMovieToHistoryEntity(movie).commentMovieEntity
            )
    }
}