package com.example.tmdb.repository

import com.example.tmdb.database.dao.MovieDao
import com.example.tmdb.database.entity.*
import com.example.tmdb.network.*
import com.example.tmdb.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface MovieRepository {
    fun getPopularMovies(): Flow<List<MovieItem>>
    fun getTopRatedMovies(): Flow<List<MovieItem>>
    fun getNowPlayingMovies(): Flow<List<MovieItem>>
    fun getUpcomingMovies(): Flow<List<MovieItem>>
    fun getMovieSearchResults(query: String): Flow<List<MovieItem>>
    fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse>
    fun getMovieCredits(movieId: Int): Flow<MovieCreditsResponse>
    fun getFavoriteMovies(): Flow<List<MovieItem>>
    suspend fun insertFavoriteMovie(movieItemDetails: MovieItemDetails)
    suspend fun deleteFavoriteMovie(movieId: Int)
    fun checkIfMovieExists(movieId: Int): Flow<Boolean>
    fun getMovieWithDetails(movieId: Int): Flow<MovieWithDetails>
}

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao
) : MovieRepository {
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

    override fun getFavoriteMovies(): Flow<List<MovieItem>> =
        movieDao.getAllMovies().map { moviesList ->
            moviesList.map { dbMovieItem ->
                dbMovieItem.toMovieItem()
            }
        }

    override suspend fun insertFavoriteMovie(movieItemDetails: MovieItemDetails) {
        movieItemDetails.let {
            val movieId = it.movieId
            movieDao.insertMovie(movie = it.toDbMovie())

            it.genres.forEach { genre ->
                movieDao.insertGenre(genre = genre.toDbGenre())
                movieDao.insertMovieGenreCrossRef(
                    MovieGenreCrossRef(
                        movieId = movieId,
                        genreId = genre.id
                    )
                )
            }

            it.cast.forEach { castMember ->
                movieDao.insertCastMember(castMember = castMember.toDbCastMember())
                movieDao.insertMovieCastCrossRef(
                    MovieCastCrossRef(
                        movieId = movieId,
                        castId = castMember.id
                    )
                )
            }

            it.crew.forEach { crewMember ->
                movieDao.insertCrewMember(crewMember = crewMember.toDbCrewMember())
                movieDao.insertMovieCrewCrossRef(
                    MovieCrewCrossRef(
                        movieId = movieId,
                        crewId = crewMember.id
                    )
                )
            }
        }
    }

    override suspend fun deleteFavoriteMovie(movieId: Int) {
        movieDao.deleteMovie(movieId = movieId)
    }

    override fun checkIfMovieExists(movieId: Int) = movieDao.checkIfMovieExists(movieId = movieId)

    override fun getMovieWithDetails(movieId: Int): Flow<MovieWithDetails> =
        movieDao.getMovieDetails(movieId = movieId)

    override fun getMovieSearchResults(query: String): Flow<List<MovieItem>> {
        TODO("Not yet implemented")
    }

    private val sharingScope = CoroutineScope(Dispatchers.Default)

    private val popularMoviesFlow = getSharedFlow(MovieCategory.PopularMovies)
    private val topRatedMoviesFlow = getSharedFlow(MovieCategory.TopRatedMovies)
    private val nowPlayingMoviesFlow = getSharedFlow(MovieCategory.NowPlayingMovies)
    private val upcomingMoviesFlow = getSharedFlow(MovieCategory.UpcomingMovies)

    private fun getSharedFlow(movieCategory: MovieCategory): Flow<List<MovieItem>> = flow {
        when (movieCategory) {
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
        object PopularMovies : MovieCategory()
        object TopRatedMovies : MovieCategory()
        object NowPlayingMovies : MovieCategory()
        object UpcomingMovies : MovieCategory()
    }
}
