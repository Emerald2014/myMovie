package ru.kudesnik.mymovie.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.repository.Repository


class DetailsViewModel(private val repository: Repository) : ViewModel() {
    private val localeLiveData: MutableLiveData<AppState> = MutableLiveData()
    val movieLiveData: LiveData<AppState>
        get() {
            return localeLiveData
        }

    fun loadData(id: Int) = with(viewModelScope) {
        localeLiveData.value = AppState.Loading

        launch(Dispatchers.IO) {
            val data = repository.getMoviesFromServer(id)
            repository.saveHistoryEntity(data)
            withContext(Dispatchers.Main) { localeLiveData.value = AppState.Success(listOf(data)) }
        }

/*        Thread {
            val data = repository.getMoviesFromServer(id)
            localeLiveData.postValue(AppState.Success(listOf(data)))
        }.start()*/
    }
}