package com.example.tmdb.database.dao

import androidx.room.*
import com.example.tmdb.database.entity.DbMovie
import kotlinx.coroutines.flow.Flow

const val MOVIE_TABLE_NAME = "movie"
const val MOVIE_ID_COLUMN = "movie_id"

@Dao
interface MovieDao {

    @Query("SELECT * FROM $MOVIE_TABLE_NAME")
    fun getAllMovies(): Flow<List<DbMovie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: DbMovie)

    @Query("DELETE FROM $MOVIE_TABLE_NAME WHERE $MOVIE_ID_COLUMN = :movieId")
    suspend fun deleteMovie(movieId: Int)

    /*@Query("SELECT EXISTS(SELECT * FROM $MOVIE_TABLE_NAME WHERE $MOVIE_ID_COLUMN = :movieId)")
    fun checkIfMovieExists(movieId: Int): Flow<Boolean>*/
}
