package com.example.tmdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.ui.screens.shared.components.MovieItemViewState
import kotlinx.coroutines.flow.*
import com.example.tmdb.data.MovieRepositoryImpl.MovieCategory
import com.example.tmdb.data.toMovieItemViewState

class HomeViewModel(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    val popularMoviesStateFlow = getCombinedFlow(MovieCategory.PopularMovies)
    val topRatedMoviesStateFlow = getCombinedFlow(MovieCategory.TopRatedMovies)
    val nowPlayingMoviesStateFlow = getCombinedFlow(MovieCategory.NowPlayingMovies)
    val upcomingMoviesStateFlow = getCombinedFlow(MovieCategory.UpcomingMovies)

    suspend fun toggleFavorite(movieId: Int) {
        movieRepository.toggleFavorite(movieId = movieId)
    }

    private fun getCombinedFlow(movieCategory: MovieCategory): StateFlow<List<MovieItemViewState>> {
        val moviesFlow = when(movieCategory) {
            is MovieCategory.PopularMovies -> movieRepository.getPopularMovies()
            is MovieCategory.TopRatedMovies -> movieRepository.getTopRatedMovies()
            is MovieCategory.NowPlayingMovies -> movieRepository.getNowPlayingMovies()
            is MovieCategory.UpcomingMovies -> movieRepository.getUpcomingMovies()
        }

        return combine(
            moviesFlow,
            movieRepository.getFavoriteMovies()
        ) { movieList, favoriteList ->
            movieList.map { movieItem ->
                toMovieItemViewState(
                    movieItem = movieItem,
                    isFavorite = favoriteList.map { movie -> movie.id }.contains(movieItem.id)
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            initialValue = emptyList()
        )
    }
}
