package com.example.tmdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.repository.MovieRepository
import com.example.tmdb.ui.screens.MovieItemDetailViewState
import com.example.tmdb.utils.toMovieItemDetailViewState
import com.example.tmdb.utils.toMovieItemDetailsViewState
import kotlinx.coroutines.flow.*

class DetailsViewModel(
    movieRepository: MovieRepository,
    movieId: Int
) : ViewModel() {
    private val movieExistsFlow = MutableStateFlow(false)

    val movieDetailsStateFlow: StateFlow<MovieItemDetailViewState> = if (!movieExistsFlow.value) {
        movieRepository.getMovieDetails(movieId = movieId)
            .zip(movieRepository.getMovieCredits(movieId = movieId)) { movieDetails, movieCredits ->
                movieDetails.toMovieItemDetailsViewState(movieCreditsResponse = movieCredits)
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                MovieItemDetailViewState()
            )
    } else {
        movieRepository.getMovieWithDetails(movieId = movieId).map {
            it.toMovieItemDetailViewState()
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            MovieItemDetailViewState()
        )
    }

    /*
    private val movieDetailsFlow = movieRepository.getMovieDetails(movieId = movieId)
    private val movieCreditsFlow = movieRepository.getMovieCredits(movieId = movieId)

    val movieDetailsStateFlow = movieDetailsFlow.zip(movieCreditsFlow) { movieDetails, movieCredits ->
        movieDetails.toMovieItemDetailsViewState(movieCreditsResponse = movieCredits)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        MovieItemDetailViewState()
    )*/
}
