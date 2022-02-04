package ru.kudesnik.mymovie.ui.favourite

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.repository.Repository

class FavouriteViewModel(private val repository: Repository) : ViewModel() {
    val favouriteLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getAllFavourites() {
        favouriteLiveData.value = AppState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            favouriteLiveData.postValue(AppState.Success(repository.getAllFavourites()))
        }
    }
}