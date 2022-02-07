package ru.kudesnik.mymovie.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.entities.MovieCategory
import ru.kudesnik.mymovie.model.entities.rest.rest_entities.MovieListKP
import ru.kudesnik.mymovie.model.repository.Repository

class ListViewModel(private val repository: Repository) : ViewModel() {
    private val liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveData

    fun getMovieFromLocalSource(movieCategory: MovieCategory) =
        getDataFromLocalSource(movieCategory)

    fun getMovieListFromServer(genres: String, isShort: Boolean) {
        liveData.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveData.postValue(AppState.Success(repository.getMovieListFromServer(genres, isShort)))
        }.start()
    }

    private fun getDataFromLocalSource(movieCategory: MovieCategory) {
        liveData.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveData.postValue(
                when (movieCategory) {
                    MovieCategory.MULT -> AppState.Success(repository.getMoviesFromLocalStorageMult())
                    MovieCategory.ACTION -> AppState.Success(repository.getMoviesFromLocalStorageAction())
                    MovieCategory.COMEDY -> AppState.Success(repository.getMoviesFromLocalStorageComedy())
                    MovieCategory.FANTASTIC -> AppState.Success(repository.getMoviesFromLocalStorageFantastic())
                }
            )
        }.start()
    }
}