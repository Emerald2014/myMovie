package ru.kudesnik.mymovie.model.entities.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRepo {
    val api: MovieAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilder())
            .build()

        adapter.create(MovieAPI::class.java)
    }

    val apiList: MovieListAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilder())
            .build()

        adapter.create(MovieListAPI::class.java)
    }

    val apiPerson: PersonAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilder())
            .build()

        adapter.create(PersonAPI::class.java)
    }
}