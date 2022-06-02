package com.example.tmdb.network

import android.content.Context
import com.example.tmdb.BuildConfig
import com.example.tmdb.utils.ConnectionState
import com.example.tmdb.utils.currentConnectivityState
import io.ktor.client.*
import io.ktor.client.request.*

interface MovieApi {
    suspend fun getPopularMovies(): MoviesResponse
    suspend fun getTopRatedMovies(): MoviesResponse
    suspend fun getNowPlayingMovies(): MoviesResponse
    suspend fun getUpcomingMovies(): MoviesResponse
    suspend fun getMovieSearchResults(query: String): MoviesResponse
    suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse
    suspend fun getMovieCredits(movieId: Int): MovieCreditsResponse
}

internal class MovieApiImpl(
    private val httpClient: HttpClient,
    context: Context
) : MovieApi {
    private val isConnected = context.currentConnectivityState == ConnectionState.Available

    override suspend fun getPopularMovies(): MoviesResponse =
        if (isConnected) httpClient.get(urlString = "${ApiConstants.BASE_URL}popular?api_key=${ApiConstants.API_KEY}")
        else MoviesResponse(movieList = emptyList())

    override suspend fun getTopRatedMovies(): MoviesResponse =
        if (isConnected) httpClient.get(urlString = "${ApiConstants.BASE_URL}top_rated?api_key=${ApiConstants.API_KEY}")
        else MoviesResponse(movieList = emptyList())

    override suspend fun getNowPlayingMovies(): MoviesResponse =
        if (isConnected) httpClient.get(urlString = "${ApiConstants.BASE_URL}now_playing?api_key=${ApiConstants.API_KEY}")
        else MoviesResponse(movieList = emptyList())

    override suspend fun getUpcomingMovies(): MoviesResponse =
        if (isConnected) httpClient.get(urlString = "${ApiConstants.BASE_URL}upcoming?api_key=${ApiConstants.API_KEY}")
        else MoviesResponse(movieList = emptyList())

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse =
        if (isConnected) httpClient.get(urlString = "${ApiConstants.BASE_URL}$movieId?api_key=${ApiConstants.API_KEY}")
        else MovieDetailsResponse()

    override suspend fun getMovieCredits(movieId: Int): MovieCreditsResponse =
        if (isConnected) httpClient.get(urlString = "${ApiConstants.BASE_URL}$movieId/credits?api_key=${ApiConstants.API_KEY}")
        else MovieCreditsResponse()

    override suspend fun getMovieSearchResults(query: String): MoviesResponse {
        TODO("Not yet implemented")
    }
}

object ApiConstants {
    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://api.themoviedb.org/3/movie/"
}
