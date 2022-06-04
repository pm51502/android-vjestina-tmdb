package com.example.tmdb.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cast")
data class DbCastMember(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cast_id")
    val castId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "character")
    val character: String,
    @ColumnInfo(name = "profile_path")
    val profilePath: String?
)
