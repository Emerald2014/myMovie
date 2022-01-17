package ru.kudesnik.mymovie.ui.main

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.withContext
import ru.kudesnik.mymovie.MainActivity
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.entities.MovieCategory
import ru.kudesnik.mymovie.model.repository.Repository
import ru.kudesnik.mymovie.model.repository.RepositoryImpl
import java.lang.Thread.sleep
import kotlin.coroutines.coroutineContext

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) :
    ViewModel() {
    fun getLiveData() = liveDataToObserve
    fun getMovieCategory() = getDataFromLocaleSource()


    private fun getDataFromLocaleSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserve.postValue(AppState.SuccessCat(repositoryImpl.getMovieCategoryFromLocalStorage()))
        }.start()
    }
}