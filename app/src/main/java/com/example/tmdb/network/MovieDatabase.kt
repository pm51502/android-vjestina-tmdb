package com.example.tmdb.network

interface MovieDatabase {
    fun getFavoriteMovies(): List<MovieItem>
    fun toggleFavorite(movie: MovieItem)
}

internal class MovieDatabaseImpl: MovieDatabase {
    private val favoriteMovies = mutableListOf<MovieItem>()

    override fun getFavoriteMovies(): List<MovieItem> = favoriteMovies

    override fun toggleFavorite(movie: MovieItem) {
        if(favoriteMovies.contains(movie)) favoriteMovies.remove(movie)
        else favoriteMovies.add(movie)
    }
}
