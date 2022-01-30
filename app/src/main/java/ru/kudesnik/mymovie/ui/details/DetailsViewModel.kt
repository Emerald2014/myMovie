package ru.kudesnik.mymovie.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.repository.Repository


class DetailsViewModel(private val repository: Repository) : ViewModel() {
    private val localeLiveData: MutableLiveData<AppState> = MutableLiveData()
    val movieLiveData: LiveData<AppState>
        get() {
            return localeLiveData
        }

    fun loadData(id: Int) {
        localeLiveData.value = AppState.Loading
        Thread {
            val data = repository.getMoviesFromServer(id)
            localeLiveData.postValue(AppState.Success(listOf(data)))
        }.start()
    }
}