package com.example.tmdb.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("results")
    val movieList: List<MovieItem>
)

@Serializable
data class MovieItem(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieItem

        if (id != other.id) return false
        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + overview.hashCode()
        return result
    }
}

@Serializable
data class MovieDetailsResponse(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("vote_average")
    val voteAverage: Float = 0F,
    @SerialName("original_title")
    val originalTitle: String = "",
    @SerialName("release_date")
    val releaseDate: String = "",
    @SerialName("original_language")
    val originalLanguage: String = "",
    @SerialName("genres")
    val genres: List<Genre> = emptyList(),
    @SerialName("runtime")
    val runtime: String = "",
    @SerialName("poster_path")
    val posterPath: String? = "",
    @SerialName("overview")
    val overview: String = ""
)

@Serializable
data class Genre(
    @SerialName("name")
    val name: String
)

@Serializable
data class MovieCreditsResponse(
    @SerialName("cast")
    val cast: List<CastMember> = emptyList(),
    @SerialName("crew")
    val crew: List<CrewMember> = emptyList()
)

@Serializable
data class CastMember(
    @SerialName("name")
    val name: String,
    @SerialName("character")
    val character: String,
    @SerialName("profile_path")
    var profilePath: String?
)

@Serializable
data class CrewMember(
    @SerialName("name")
    val name: String,
    @SerialName("job")
    val job: String
)
