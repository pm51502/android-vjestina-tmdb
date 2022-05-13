package com.example.tmdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.utils.MovieItemDetail
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DetailsViewModel(
    movieRepository: MovieRepository,
    movieId: Int
): ViewModel() {
    val movieDetailsStateFlow = movieRepository.getMovieDetails(movieId = movieId)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            MovieItemDetail()
        )
}
