package com.example.tmdb.data

import com.example.tmdb.ui.screens.MovieItemDetailViewState
import com.example.tmdb.ui.screens.shared.components.MovieItemViewState

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

fun MovieItemViewState.toMovieItem() : MovieItem =
    MovieItem(
        id = id,
        title = title,
        overview = overview,
        posterPath = imageUrl
    )

fun MovieItem.toMovieItemViewState(
    isFavorite: Boolean
) = MovieItemViewState(
    id = id,
    title = title,
    overview = overview,
    imageUrl = posterPath?.let { "$BASE_IMAGE_URL$it" },
    isFavorite = isFavorite
)

fun MovieDetailsResponse.toMovieDetailsViewState(
    movieCreditsResponse: MovieCreditsResponse
): MovieItemDetailViewState = MovieItemDetailViewState(
        id = id,
        userScore = voteAverage*10,
        title = originalTitle,
        year = releaseDate.split("-")[0],
        date = releaseDate.split("-").toList().reversed().joinToString("/"),
        country = originalLanguage,
        genres = genres,
        runtime = "${(runtime.toFloat()/60).toInt()}h ${(runtime.toFloat()%60).toInt()}min",
        posterPath = posterPath?.let { "$BASE_IMAGE_URL$it" },
        overview = overview,
        crew = movieCreditsResponse.crew,
        topBilledCast = movieCreditsResponse.cast.map { it.updateProfilePath() }
)

fun CastMember.updateProfilePath() = copy(
    profilePath = profilePath?.let { "$BASE_IMAGE_URL$it" }
)
