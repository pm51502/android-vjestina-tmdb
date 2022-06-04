package com.example.tmdb.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["movie_id", "genre_id"])
data class MovieGenreCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "genre_id")
    val genreId: Int
)

@Entity(primaryKeys = ["movie_id", "cast_id"])
data class MovieCastCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "cast_id")
    val castId: Int
)

@Entity(primaryKeys = ["movie_id", "crew_id"])
data class MovieCrewCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "crew_id")
    val crewId: Int
)
