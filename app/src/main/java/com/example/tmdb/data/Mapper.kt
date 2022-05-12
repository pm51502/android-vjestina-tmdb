package com.example.tmdb.data

import com.example.tmdb.ui.screens.shared.components.MovieItemViewState

fun toMovieItemViewState(
    movieItem: MovieItem,
    isFavorite: Boolean
): MovieItemViewState = MovieItemViewState(
    id = movieItem.id,
    title = movieItem.title,
    overview = movieItem.overview,
    imagePath = movieItem.imagePath,
    isFavorite = isFavorite
)
