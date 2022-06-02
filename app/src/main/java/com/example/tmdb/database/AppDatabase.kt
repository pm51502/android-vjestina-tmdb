package com.example.tmdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb.database.dao.MovieDao
import com.example.tmdb.database.entity.DbMovie

@Database(entities = [DbMovie::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
