package com.example.tmdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.ui.screens.shared.components.MovieItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {

    private val _favoriteMoviesStateFlow = MutableStateFlow<List<MovieItem>>(emptyList())
    val favoriteMoviesStateFlow = _favoriteMoviesStateFlow.asStateFlow()

    private suspend fun getFavoriteMovies() {
        movieRepository.getFavoriteMovies()
            .collect { movieList -> _favoriteMoviesStateFlow.emit(movieList) }
    }

    suspend fun toggleFavorite(movieId: Int) {
        movieRepository.toggleFavorite(movieId = movieId)
    }

    init {
        viewModelScope.launch {
            getFavoriteMovies()
        }
    }
}
