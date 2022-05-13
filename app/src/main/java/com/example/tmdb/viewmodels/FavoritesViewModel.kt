package com.example.tmdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.data.toMovieItemViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {

    val favoriteMoviesStateFlow = movieRepository
        .getFavoriteMovies()
        .map { movieList ->
            movieList.map { movieItem ->
                movieItem.toMovieItemViewState(isFavorite = true)
            }
        }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            movieRepository.toggleFavorite(movieId = movieId)
        }
    }
}
