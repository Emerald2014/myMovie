package ru.kudesnik.mymovie.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.entities.Movie
import ru.kudesnik.mymovie.model.repository.Repository

class HistoryViewModel(private val repository: Repository) : ViewModel() {
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData()
    val historyLiveDataDelete = MutableLiveData<Unit>()

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            historyLiveData.postValue(AppState.Success(repository.getAllHistory()))
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            historyLiveDataDelete.postValue(repository.deleteMovie(movie))
        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            historyLiveDataDelete.postValue(repository.updateMovie(movie))
        }
    }
}




