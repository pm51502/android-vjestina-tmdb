package com.example.tmdb.di.modules

import com.example.tmdb.viewmodels.DetailsViewModel
import com.example.tmdb.viewmodels.FavoritesViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.tmdb.viewmodels.HomeViewModel

val viewModelModule = module {

    viewModel {
        HomeViewModel(movieRepository = get())
    }
    viewModel {
        FavoritesViewModel(movieRepository = get())
    }
    viewModel { params ->
        DetailsViewModel(
            movieRepository = get(),
            movieId = params.get()
        )
    }
}
