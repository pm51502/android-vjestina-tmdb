package com.example.tmdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.data.toMovieDetailsViewState
import com.example.tmdb.ui.screens.MovieItemDetailViewState
import kotlinx.coroutines.flow.*

class DetailsViewModel(
    movieRepository: MovieRepository,
    movieId: Int
): ViewModel() {
    private val movieDetailsFlow = movieRepository.getMovieDetails(movieId = movieId)
    private val movieCreditsFlow = movieRepository.getMovieCredits(movieId = movieId)

    val movieDetailsStateFlow = movieDetailsFlow.zip(movieCreditsFlow) { movieDetails, movieCredits ->
        movieDetails.toMovieDetailsViewState(movieCreditsResponse = movieCredits)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        MovieItemDetailViewState()
    )
}
