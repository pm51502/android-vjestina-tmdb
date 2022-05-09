package com.example.tmdb.data

import androidx.compose.runtime.rememberCoroutineScope
import com.example.tmdb.ui.screens.shared.components.MovieItem
import com.example.tmdb.utils.MovieItemDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface MovieRepository {
    fun getPopularMovies(): Flow<List<MovieItem>>

    fun getTopRatedMovies(): Flow<List<MovieItem>>

    fun getNowPlayingMovies(): Flow<List<MovieItem>>

    fun getUpcomingMovies(): Flow<List<MovieItem>>

    fun getMovieSearchResults(query: String): Flow<List<MovieItem>>

    fun getFavoriteMovies(): Flow<List<MovieItem>>

    fun getMovieDetails(movieId: Int): Flow<MovieItemDetail>

    suspend fun toggleFavorite(movieId: Int)
    /*suspend fun addMovieToFavorites(movie: MovieItem)

    suspend fun removeMovieFromFavorites(movieId: Int)*/
}

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
): MovieRepository  {

    override fun getPopularMovies(): Flow<List<MovieItem>> = flow {
        emit(movieApi.getPopularMovies().movieList)
    }

    override fun getTopRatedMovies(): Flow<List<MovieItem>> = flow {
        emit(movieApi.getTopRatedMovies().movieList)
    }

    override fun getNowPlayingMovies(): Flow<List<MovieItem>> = flow {
        emit(movieApi.getNowPlayingMovies().movieList)
    }

    override fun getUpcomingMovies(): Flow<List<MovieItem>> = flow {
        emit(movieApi.getUpcomingMovies().movieList)
    }

    override fun getMovieDetails(movieId: Int): Flow<MovieItemDetail> = flow {
        emit(movieApi.getMovieDetails(movieId = movieId).movieDetails)
    }

    override fun getMovieSearchResults(query: String): Flow<List<MovieItem>> {
        TODO("Not yet implemented")
    }

    /*override fun getFavoriteMovies(): Flow<List<MovieItem>> = flow {
        emit(movieDatabase.getFavoriteMovies())
    }*/

    override suspend fun toggleFavorite(movieId: Int) {
        //movieDatabase.toggleFavorite(movieId = movieId)
        movieDatabase.toggleFavorite(movieId = movieId)
        refreshFavouriteMoviesPublisher.emit(RefreshEvent)
    }

    object RefreshEvent
    private val refreshFavouriteMoviesPublisher = MutableSharedFlow<RefreshEvent>()

    private val favoriteMoviesFlow = refreshFavouriteMoviesPublisher
        .map { movieDatabase.getFavoriteMovies() }
        .shareIn(
            CoroutineScope(Dispatchers.Default),
            SharingStarted.WhileSubscribed(),
            replay = 1,
        )

    override fun getFavoriteMovies() = favoriteMoviesFlow

    /*override suspend fun markMovieFavourite(movie: Movie, isFavourite: Boolean) {
        movieDatabase.saveIsMovieFavourite(movie, isFavourite)
        refreshFavouriteMoviesPublisher.emit(RefreshEvent)
    }*/

    /*override suspend fun addMovieToFavorites(movie: MovieItem) {
        movieDatabase.addMovieToFavorites(movie = movie)
    }

    override suspend fun removeMovieFromFavorites(movieId: Int) {
        movieDatabase.removeMovieFromFavorites(movieId = movieId)
    }*/
}