package com.example.tmdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.ui.screens.shared.components.MovieItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _popularMoviesStateFlow = MutableStateFlow<List<MovieItem>>(emptyList())
    val popularMoviesStateFlow = _popularMoviesStateFlow.asStateFlow()

    private val _topRatedMoviesStateFlow = MutableStateFlow<List<MovieItem>>(emptyList())
    val topRatedMoviesStateFlow = _topRatedMoviesStateFlow.asStateFlow()

    private val _nowPlayingMoviesStateFlow = MutableStateFlow<List<MovieItem>>(emptyList())
    val nowPlayingMoviesStateFlow = _nowPlayingMoviesStateFlow.asStateFlow()

    private val _upcomingMoviesStateFlow = MutableStateFlow<List<MovieItem>>(emptyList())
    val upcomingMoviesStateFlow = _upcomingMoviesStateFlow.asStateFlow()

    private suspend fun getPopularMovies() {
        movieRepository.getPopularMovies()
            .collect { movieList -> _popularMoviesStateFlow.emit(movieList) }
    }

    private suspend fun getTopRatedMovies() {
        movieRepository.getTopRatedMovies()
            .collect { movieList -> _topRatedMoviesStateFlow.emit(movieList) }
    }

    private suspend fun getNowPlayingMovies() {
        movieRepository.getNowPlayingMovies()
            .collect { movieList -> _nowPlayingMoviesStateFlow.emit(movieList) }
    }

    private suspend fun getUpcomingMovies() {
        movieRepository.getUpcomingMovies()
            .collect { movieList -> _upcomingMoviesStateFlow.emit(movieList) }
    }

    suspend fun toggleFavorite(movieId: Int) {
        movieRepository.toggleFavorite(movieId = movieId)

        getPopularMovies()
        getTopRatedMovies()
        getNowPlayingMovies()
        getUpcomingMovies()
    }

    init {
        viewModelScope.launch {
            getPopularMovies()
            getTopRatedMovies()
            getNowPlayingMovies()
            getUpcomingMovies()
        }
    }
}
