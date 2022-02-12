package ru.kudesnik.mymovie.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.repository.Repository

class MapsViewModel(private val repository: Repository) : ViewModel() {
    private val localeLiveData: MutableLiveData<AppState> = MutableLiveData()
    val movieLiveData: LiveData<AppState>
        get() {
            return localeLiveData
        }

    fun loadPerson(id: Int) = with(viewModelScope) {
        localeLiveData.value = AppState.Loading
        launch(Dispatchers.IO) {
            val dataPerson = repository.getPersonFromServer(id)
            withContext(Dispatchers.Main) {
                localeLiveData.value = AppState.SuccessPers(listOf(dataPerson))
            }

        }
    }
}