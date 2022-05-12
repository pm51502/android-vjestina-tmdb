package com.example.tmdb.data

import com.example.tmdb.utils.MovieItemDetail

data class MovieResponse(
    val movieList: List<MovieItem>
)

data class MovieDetailsResponse(
    val movieDetails: MovieItemDetail
)
