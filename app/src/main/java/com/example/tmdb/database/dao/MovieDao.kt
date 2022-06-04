package com.example.tmdb.database.dao

import androidx.room.*
import com.example.tmdb.database.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT movie_id, title, overview, poster_path FROM movie")
    fun getAllMovies(): Flow<List<DbMovieItem>>

    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    fun getMovieDetails(movieId: Int): Flow<MovieWithDetails>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: DbMovie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenre(genre: DbGenre)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCastMember(castMember: DbCastMember)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrewMember(crewMember: DbCrewMember)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieGenreCrossRef(movieGenreCrossRef: MovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieCastCrossRef(movieCastCrossRef: MovieCastCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieCrewCrossRef(movieCrewCrossRef: MovieCrewCrossRef)

    @Query("DELETE FROM movie WHERE movie_id = :movieId")
    suspend fun deleteMovie(movieId: Int)

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE movie_id = :movieId)")
    fun checkIfMovieExists(movieId: Int): Flow<Boolean>
}
