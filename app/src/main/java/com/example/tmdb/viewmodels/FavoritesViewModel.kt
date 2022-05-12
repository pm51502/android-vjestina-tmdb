package com.example.tmdb.viewmodels

import androidx.lifecycle.ViewModel
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.data.toMovieItemViewState
import kotlinx.coroutines.flow.map

class FavoritesViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {

    val favoriteMoviesStateFlow = movieRepository
        .getFavoriteMovies()
        .map { movieList ->
            movieList.map { movieItem ->
                toMovieItemViewState(
                    movieItem = movieItem,
                    isFavorite = true
                )
            }
        }

    suspend fun toggleFavorite(movieId: Int) {
        movieRepository.toggleFavorite(movieId = movieId)
    }
}
