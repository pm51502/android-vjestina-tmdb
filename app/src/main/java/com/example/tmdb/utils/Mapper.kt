package com.example.tmdb.utils

import com.example.tmdb.database.entity.*
import com.example.tmdb.network.*
import com.example.tmdb.ui.screens.MovieItemDetailViewState
import com.example.tmdb.ui.screens.shared.components.MovieItemViewState

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

fun MovieItem.toMovieItemViewState(
    isFavorite: Boolean
) = MovieItemViewState(
    id = id,
    title = title,
    overview = overview,
    imageUrl = posterPath?.let { "$BASE_IMAGE_URL$it" },
    isFavorite = isFavorite
)

fun MovieDetailsResponse.toMovieItemDetailsViewState(
    movieCreditsResponse: MovieCreditsResponse
): MovieItemDetailViewState = MovieItemDetailViewState(
    id = id,
    userScore = voteAverage * 10,
    title = originalTitle,
    year = releaseDate.split("-")[0],
    date = releaseDate.split("-").toList().reversed().joinToString("/"),
    country = originalLanguage,
    genres = genres,
    runtime = "${(runtime.toFloat() / 60).toInt()}h ${(runtime.toFloat() % 60).toInt()}min",
    posterPath = posterPath?.let { "$BASE_IMAGE_URL$it" },
    overview = overview,
    crew = movieCreditsResponse.crew,
    topBilledCast = movieCreditsResponse.cast.map { it.updateProfilePath() }
)

fun CastMember.updateProfilePath() = copy(
    profilePath = profilePath?.let { "$BASE_IMAGE_URL$it" }
)

data class MovieItemDetails(
    val movieId: Int = 0,
    val voteAverage: Float = 0F,
    val title: String = "",
    val releaseDate: String = "",
    val originalLanguage: String = "",
    val runtime: String = "",
    val posterPath: String? = "",
    val overview: String = "",
    val genres: List<Genre> = emptyList(),
    val cast: List<CastMember> = emptyList(),
    val crew: List<CrewMember> = emptyList()
)

fun MovieDetailsResponse.toMovieItemDetails(
    movieCreditsResponse: MovieCreditsResponse
): MovieItemDetails = MovieItemDetails(
    movieId = id,
    voteAverage = voteAverage,
    title = originalTitle,
    releaseDate = releaseDate,
    originalLanguage = originalLanguage,
    runtime = runtime,
    posterPath = posterPath?.let { "$BASE_IMAGE_URL$it" },
    overview = overview,
    genres = genres,
    cast = movieCreditsResponse.cast.map { it.updateProfilePath() },
    crew = movieCreditsResponse.crew
)

fun MovieItemDetails.toDbMovie() = DbMovie(
    movieId = movieId,
    voteAverage = voteAverage,
    title = title,
    releaseDate = releaseDate,
    originalLanguage = originalLanguage,
    runtime = runtime,
    posterPath = posterPath,
    overview = overview
)

fun Genre.toDbGenre() = DbGenre(
    genreId = id,
    name = name
)

fun CastMember.toDbCastMember() = DbCastMember(
    castId = id,
    name = name,
    character = character,
    profilePath = profilePath
)

fun CrewMember.toDbCrewMember() = DbCrewMember(
    crewId = id,
    name = name,
    job = job
)

fun DbGenre.toGenre() = Genre(
    id = genreId,
    name = name
)

fun DbCastMember.toCastMember() = CastMember(
    id = castId,
    name = name,
    character = character,
    profilePath = profilePath
)

fun DbCrewMember.toCrewMember() = CrewMember(
    id = crewId,
    name = name,
    job = job
)

fun DbMovieItem.toMovieItem() = MovieItem(
    id = id,
    title = title,
    overview = overview,
    posterPath = imageUrl
)

fun MovieWithDetails.toMovieItemDetailViewState() = MovieItemDetailViewState(
    id = movie.movieId,
    userScore = movie.voteAverage * 10,
    title = movie.title,
    year = movie.releaseDate.split("-")[0],
    date = movie.releaseDate.split("-").toList().reversed().joinToString("/"),
    country = movie.originalLanguage,
    genres = genres.map { it.toGenre() },
    runtime = movie.runtime,
    posterPath = movie.posterPath,
    overview = movie.overview,
    crew = crew.map { it.toCrewMember() },
    topBilledCast = cast.map { it.toCastMember() }
)
