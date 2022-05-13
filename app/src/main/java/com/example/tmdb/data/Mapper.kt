package com.example.tmdb.data

import com.example.tmdb.ui.screens.shared.components.MovieItemViewState

fun MovieItem.toMovieItemViewState(
    isFavorite: Boolean
): MovieItemViewState = MovieItemViewState(
    id = id,
    title = title,
    overview = overview,
    imagePath = imagePath,
    isFavorite = isFavorite
)
