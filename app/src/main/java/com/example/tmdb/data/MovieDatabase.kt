package com.example.tmdb.data

import android.util.Log
import com.example.tmdb.ui.screens.shared.components.MovieItem
import com.example.tmdb.utils.MovieLoader

interface MovieDatabase {
    fun getFavoriteMovies(): List<MovieItem>

    fun toggleFavorite(movieId: Int)
}

internal class MovieDatabaseImpl: MovieDatabase {

    private var favoriteMovies = mutableListOf<MovieItem>()

    override fun getFavoriteMovies(): List<MovieItem> {
        //Log.i("RETURN FAV LIST", favoriteMovies.toString())
        //return MovieLoader.getFavoriteMovies()
        return favoriteMovies
    }

    override fun toggleFavorite(movieId: Int) {
        val updatedMovie = MovieLoader.toggleFavorite(movieId = movieId)
        //val updatedMovie = MovieLoader.getMovieById(movieId = movieId)

        Log.i("TOGGLE", updatedMovie.toString())

        if(updatedMovie.isFavorite) {
            favoriteMovies.add(updatedMovie)
        }
        else {
            updatedMovie.isFavorite = true
            favoriteMovies.remove(updatedMovie)
        }

        Log.i("FAVORITES", favoriteMovies.toString())
    }
}
