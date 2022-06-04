package com.example.tmdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.network.MovieCreditsResponse
import com.example.tmdb.network.MovieDetailsResponse
import com.example.tmdb.ui.screens.shared.components.MovieItemViewState
import kotlinx.coroutines.flow.*
import com.example.tmdb.repository.MovieRepositoryImpl.MovieCategory
import com.example.tmdb.repository.MovieRepository
import com.example.tmdb.utils.toMovieItemDetails
import com.example.tmdb.utils.toMovieItemViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    val popularMoviesStateFlow = getCombinedFlow(MovieCategory.PopularMovies)
    val topRatedMoviesStateFlow = getCombinedFlow(MovieCategory.TopRatedMovies)
    val nowPlayingMoviesStateFlow = getCombinedFlow(MovieCategory.NowPlayingMovies)
    val upcomingMoviesStateFlow = getCombinedFlow(MovieCategory.UpcomingMovies)

    private val movieDetailsFlow = MutableStateFlow<MovieDetailsResponse?>(null)
    private val movieCreditsFlow = MutableStateFlow<MovieCreditsResponse?>(null)

    fun insertFavoriteMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            movieRepository.getMovieDetails(movieId = movieId).collect { movieDetailsFlow.emit(it) }
            movieRepository.getMovieCredits(movieId = movieId).collect { movieCreditsFlow.emit(it) }

            movieDetailsFlow.value?.let { movieDetails ->
                movieCreditsFlow.value?.let { movieCredits ->
                    movieRepository.insertFavoriteMovie(
                        movieItemDetails = movieDetails.toMovieItemDetails(
                            movieCreditsResponse = movieCredits
                        )
                    )
                }
            }
        }
    }

    fun deleteFavoriteMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            movieRepository.deleteFavoriteMovie(movieId = movieId)
        }
    }

    private fun getCombinedFlow(movieCategory: MovieCategory): StateFlow<List<MovieItemViewState>> {
        val moviesFlow = when (movieCategory) {
            is MovieCategory.PopularMovies -> movieRepository.getPopularMovies()
            is MovieCategory.TopRatedMovies -> movieRepository.getTopRatedMovies()
            is MovieCategory.NowPlayingMovies -> movieRepository.getNowPlayingMovies()
            is MovieCategory.UpcomingMovies -> movieRepository.getUpcomingMovies()
        }

        return combine(
            moviesFlow,
            movieRepository.getFavoriteMovies()
        ) { movieList, favoriteList ->
            val favoriteListIds = favoriteList.map { movie -> movie.id }
            movieList.map { movieItem ->
                movieItem.toMovieItemViewState(isFavorite = favoriteListIds.contains(movieItem.id))
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            initialValue = emptyList()
        )
    }
}
