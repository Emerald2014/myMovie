package ru.kudesnik.mymovie.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.repository.Repository
import ru.kudesnik.mymovie.model.repository.RepositoryImpl

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