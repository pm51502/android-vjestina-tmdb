package com.example.tmdb.database.entity

import androidx.room.ColumnInfo

data class DbMovieItem(
    @ColumnInfo(name = "movie_id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "poster_path")
    val imageUrl: String?
)
