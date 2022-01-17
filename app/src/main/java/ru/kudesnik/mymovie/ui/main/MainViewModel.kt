package ru.kudesnik.mymovie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.entities.MovieCategory
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData()) :
    ViewModel() {
    fun getLiveData()= liveDataToObserve
    fun getMovieCategory() = getDataFromLocaleSource()


    private fun getDataFromLocaleSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserve.postValue(AppState.SuccessCat(Any()))
        }.start()
    }
}