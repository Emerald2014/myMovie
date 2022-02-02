package ru.kudesnik.mymovie.utils

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import ru.kudesnik.mymovie.model.entities.rest.rest_entities.MovieKP
import ru.kudesnik.mymovie.model.entities.rest.rest_entities.MovieListKP
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object NetworkUtils {
    const val BASE_URL = "https://api.kinopoisk.dev/movie?"
    const val PARAMS_FIELD = "field"
    const val PARAMS_SEARCH = "search"

    const val FIELD_GENRES = "genres.name"
    const val SEARCH_GENRES = "drama"

    const val FIELD_ID = "id"

    const val FIELD_YEAR = "year"
    const val SEARCH_YEAR_ANSWER = "2020-2022"

    const val FIELD_RATINGKP = "rating.kp"
    const val SEARCH_RATINGKP = "7-10"

    const val SORT_FIELD_YEAR_LABEL = "sortField"
    const val SORT_TYPE_MINUS_ONE = "-1"
    const val SORT_TYPE_LABEL = "sortType"

    const val TOKEN_LABEL = "token"
    const val TOKEN = "ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06"

    fun uriBuilder(genres: String): String {
        return Uri.parse(BASE_URL).buildUpon()
            .appendQueryParameter(PARAMS_FIELD, FIELD_GENRES)
            .appendQueryParameter(PARAMS_SEARCH, genres)
            .appendQueryParameter(PARAMS_FIELD, FIELD_YEAR)
            .appendQueryParameter(PARAMS_SEARCH, SEARCH_YEAR_ANSWER)
            .appendQueryParameter(PARAMS_FIELD, FIELD_RATINGKP)
            .appendQueryParameter(PARAMS_SEARCH, SEARCH_RATINGKP)
            .appendQueryParameter(SORT_FIELD_YEAR_LABEL, FIELD_YEAR)
            .appendQueryParameter(SORT_TYPE_LABEL, SORT_TYPE_MINUS_ONE)
            .appendQueryParameter(TOKEN_LABEL, TOKEN)
            .build().toString()
    }

    fun loadMovie(id: Int): MovieKP? {
        try {
            val uri =
                URL("https://api.kinopoisk.dev/movie?search=${id}&field=id&token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = 10000
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))

                val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }

                return Gson().fromJson(lines, MovieKP::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    fun loadMovieList(genres: String): MovieListKP? {
        try {
            val uri = URL(uriBuilder(genres))
//                URL("https://api.kinopoisk.dev/movie?field=genres.name&search=${genres}&field=year&search=2020-2022&field=typeNumber&search=1&field=rating.kp&search=7-10&sortField=year&sortType=-1&token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = 10000
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))

                val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                return Gson().fromJson(lines, MovieListKP::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }


    private fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var tempVariable: String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rawData.append(tempVariable).append("\n")
        }
        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

}
