package com.example.tmdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.utils.MovieItemDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val movieRepository: MovieRepository,
    private val movieId: Int
): ViewModel() {

    private val _movieDetailsStateFlow = MutableStateFlow(MovieItemDetail())
    val movieDetailsStateFlow = _movieDetailsStateFlow.asStateFlow()

    private suspend fun getMovieDetails(movieId: Int) {
        movieRepository.getMovieDetails(movieId = movieId)
            .collect { movieDetails -> _movieDetailsStateFlow.emit(movieDetails) }
    }

    init {
        viewModelScope.launch {
            getMovieDetails(movieId = movieId)
        }
    }
}
