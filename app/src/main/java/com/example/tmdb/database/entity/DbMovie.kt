package com.example.tmdb.database.entity

import androidx.room.*

@Entity(tableName = "movie")
data class DbMovie(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Float,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String,
    @ColumnInfo(name = "runtime")
    val runtime: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "overview")
    val overview: String
)

data class MovieWithDetails(
    @Embedded
    val movie: DbMovie,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "genre_id",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<DbGenre>,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "cast_id",
        associateBy = Junction(MovieCastCrossRef::class)
    )
    val cast: List<DbCastMember>,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "crew_id",
        associateBy = Junction(MovieCrewCrossRef::class)
    )
    val crew: List<DbCrewMember>
)
