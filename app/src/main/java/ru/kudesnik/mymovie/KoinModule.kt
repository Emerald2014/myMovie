package ru.kudesnik.mymovie

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kudesnik.mymovie.model.repository.Repository
import ru.kudesnik.mymovie.model.repository.RepositoryImpl
import ru.kudesnik.mymovie.ui.main.MainViewModel

val appModule = module {
    single<Repository> { RepositoryImpl() }
    //View models
    viewModel { MainViewModel(get()) }
}