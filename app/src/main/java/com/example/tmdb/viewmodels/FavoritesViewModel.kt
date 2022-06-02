package com.example.tmdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.database.entity.DbMovie
import com.example.tmdb.repository.MovieRepository
import com.example.tmdb.utils.toMovieItemViewState
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

    fun addFavoriteMovie(movie: DbMovie) {
        viewModelScope.launch(Dispatchers.Default) {
            movieRepository.insertMovie(movie = movie)
        }
    }

    fun removeFavoriteMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            movieRepository.deleteMovie(movieId = movieId)
        }
    }
}

    /*fun toggleFavorite(movie: MovieItem) {
        viewModelScope.launch(Dispatchers.Default) {
            movieRepository.toggleFavorite(movie = movie)
        }
    }*/
