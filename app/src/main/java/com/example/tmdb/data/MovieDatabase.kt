package com.example.tmdb.data

import com.example.tmdb.utils.MovieLoader

interface MovieDatabase {
    fun getFavoriteMovies(): List<MovieItem>
    fun toggleFavorite(movieId: Int)
}

internal class MovieDatabaseImpl: MovieDatabase {
    private var favoriteMoviesIds = mutableListOf<Int>()

    override fun getFavoriteMovies(): List<MovieItem> =
        MovieLoader.allMovies.filter { favoriteMoviesIds.contains(it.id) }

    override fun toggleFavorite(movieId: Int) {
        if(favoriteMoviesIds.contains(movieId)) favoriteMoviesIds.remove(movieId)
        else favoriteMoviesIds.add(movieId)
    }
}
