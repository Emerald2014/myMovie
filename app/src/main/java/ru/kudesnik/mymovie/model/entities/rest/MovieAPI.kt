package ru.kudesnik.mymovie.model.entities.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kudesnik.mymovie.model.entities.rest.rest_entities.MovieKP
import ru.kudesnik.mymovie.model.entities.rest.rest_entities.MovieListKP
import ru.kudesnik.mymovie.model.entities.rest.rest_entities.PersonsKP


interface MovieAPI {
    @GET("movie")
    fun getMovie(
        @Query("search") id: Int,
        @Query("field") idLabel: String,
        @Query("token") token: String
    ): Call<MovieKP>
}

interface MovieListAPI {
    @GET("movie")
    fun getMovieList(
        @Query("field") genresNameLabel: String = "genres.name",
        @Query("search") genresName: String,
        @Query("field") yearLabel: String = "year",
        @Query("search") year: String = "2020-2022",
        @Query("field") shortMovieLengthLabel: String = "movieLength",
        @Query("search") shortMovieLength: String,
        @Query("field") typeNumberLabel: String = "typeNumber",
        @Query("search") typeNumber: Int = 1,
        @Query("field") ratingKPLabel: String = "rating.kp",
        @Query("search") ratingKP: String = "7-10",
        @Query("sortField") yearSortLabel: String = yearLabel,
        @Query("sortType") sortType: String = "-1",
        @Query("token") token: String
    ): Call<MovieListKP>
}

interface PersonAPI {
    @GET("person")
    fun getPerson(
        @Query("search") id: Int,
        @Query("field") idLabel: String = "id",
        @Query("token") token: String
    ): Call<PersonsKP>
}

//URL("https://api.kinopoisk.dev/movie?search=${id}&field=id&token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06")
// URL("https://api.kinopoisk.dev/movie?
// field=genres.name&search=${genres}&
// field=year&search=2020-2022&
// field=typeNumber&search=1&
// field=rating.kp&search=7-10&
// sortField=year&sortType=-1&
// token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06")
