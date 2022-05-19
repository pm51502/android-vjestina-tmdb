package com.example.tmdb.data

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
    fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse>
    fun getMovieCredits(movieId: Int): Flow<MovieCreditsResponse>
    suspend fun toggleFavorite(movie: MovieItem)
}

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
): MovieRepository  {
    override fun getPopularMovies(): Flow<List<MovieItem>> = popularMoviesFlow

    override fun getTopRatedMovies(): Flow<List<MovieItem>> = topRatedMoviesFlow

    override fun getNowPlayingMovies(): Flow<List<MovieItem>> = nowPlayingMoviesFlow

    override fun getUpcomingMovies(): Flow<List<MovieItem>> = upcomingMoviesFlow

    override fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse> = flow {
        emit(movieApi.getMovieDetails(movieId = movieId))
    }

    override fun getMovieCredits(movieId: Int): Flow<MovieCreditsResponse> = flow {
        emit(movieApi.getMovieCredits(movieId = movieId))
    }

    override fun getMovieSearchResults(query: String): Flow<List<MovieItem>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteMovies(): Flow<List<MovieItem>> = favoriteMoviesFlow

    override suspend fun toggleFavorite(movie: MovieItem) {
        movieDatabase.toggleFavorite(movie = movie)
        refreshFavouriteMoviesPublisher.emit(RefreshEvent)
    }

    private val sharingScope = CoroutineScope(Dispatchers.Default)

    private val popularMoviesFlow = getSharedFlow(MovieCategory.PopularMovies)
    private val topRatedMoviesFlow =  getSharedFlow(MovieCategory.TopRatedMovies)
    private val nowPlayingMoviesFlow =  getSharedFlow(MovieCategory.NowPlayingMovies)
    private val upcomingMoviesFlow =  getSharedFlow(MovieCategory.UpcomingMovies)

    object RefreshEvent
    private val refreshFavouriteMoviesPublisher = MutableSharedFlow<RefreshEvent>(replay = 1)

    private val favoriteMoviesFlow = refreshFavouriteMoviesPublisher
        .onStart { refreshFavouriteMoviesPublisher.emit(RefreshEvent) }
        .map { movieDatabase.getFavoriteMovies() }
        .shareIn(
            sharingScope,
            SharingStarted.Lazily,
            replay = 1,
        )

    private fun getSharedFlow(movieCategory: MovieCategory): Flow<List<MovieItem>> = flow {
        when(movieCategory) {
            is MovieCategory.PopularMovies -> emit(movieApi.getPopularMovies().movieList)
            is MovieCategory.TopRatedMovies -> emit(movieApi.getTopRatedMovies().movieList)
            is MovieCategory.NowPlayingMovies -> emit(movieApi.getNowPlayingMovies().movieList)
            is MovieCategory.UpcomingMovies -> emit(movieApi.getUpcomingMovies().movieList)
        }
    }.shareIn(
        sharingScope,
        SharingStarted.Lazily,
        replay = 1,
    )

    sealed class MovieCategory {
        object PopularMovies: MovieCategory()
        object TopRatedMovies: MovieCategory()
        object NowPlayingMovies: MovieCategory()
        object UpcomingMovies: MovieCategory()
    }
}
