package ru.kudesnik.mymovie

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kudesnik.mymovie.model.repository.Repository
import ru.kudesnik.mymovie.model.repository.RepositoryImpl
import ru.kudesnik.mymovie.ui.details.DetailsViewModel
import ru.kudesnik.mymovie.ui.favourite.FavouriteViewModel
import ru.kudesnik.mymovie.ui.list.ListViewModel

val appModule = module {
    single<Repository> { RepositoryImpl() }
    //View models
    viewModel { ListViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { FavouriteViewModel(get()) }
}