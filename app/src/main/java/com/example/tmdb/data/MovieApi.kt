package com.example.tmdb.data

import com.example.tmdb.utils.MovieLoader

interface MovieApi {
    suspend fun getPopularMovies(): MovieResponse

    suspend fun getTopRatedMovies(): MovieResponse

    suspend fun getNowPlayingMovies(): MovieResponse

    suspend fun getUpcomingMovies(): MovieResponse

    suspend fun getMovieSearchResults(query: String): MovieResponse

    suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse
}

internal class MovieApiImpl: MovieApi {

    override suspend fun getPopularMovies(): MovieResponse =
        MovieResponse(movieList = MovieLoader.allMovies.slice(0..2).toList())

    override suspend fun getTopRatedMovies(): MovieResponse =
        MovieResponse(movieList = MovieLoader.allMovies.slice(3..5).toList())

    override suspend fun getNowPlayingMovies(): MovieResponse =
        MovieResponse(movieList = MovieLoader.allMovies.slice(6..8).toList())

    override suspend fun getUpcomingMovies(): MovieResponse =
        MovieResponse(movieList = MovieLoader.allMovies.slice(9..11).toList())

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse =
        MovieDetailsResponse(movieDetails = MovieLoader.getMovieDetails(movieId = movieId))

    override suspend fun getMovieSearchResults(query: String): MovieResponse {
        TODO("Not yet implemented")
    }
}
