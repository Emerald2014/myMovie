package ru.kudesnik.mymovie.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.repository.Repository

class ListViewModel(private val repository: Repository) : ViewModel() {
    private val liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveData

    fun getMovieFromLocalSourceComedy() = getDataFromLocalSource(true)
    fun getMovieFromLocalSourceAction() = getDataFromLocalSource(false)

    private fun getDataFromLocalSource(isComedy: Boolean) {
        liveData.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveData.postValue(
                if (isComedy) {
                    AppState.Success(repository.getMoviesFromLocalStorageComedy())
                } else {
                    AppState.Success(repository.getMoviesFromLocalStorageAction())

                }
            )
        }.start()
    }
}