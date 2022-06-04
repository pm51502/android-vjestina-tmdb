package com.example.tmdb.di.modules

import androidx.room.Room
import com.example.tmdb.database.AppDatabase
import com.example.tmdb.network.*
import com.example.tmdb.network.MovieApiImpl
import com.example.tmdb.repository.MovieRepository
import com.example.tmdb.repository.MovieRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "movie-db"
        ).build()
    }
    single {
        val database = get<AppDatabase>()
        database.movieDao()
    }
    single<MovieRepository> {
        MovieRepositoryImpl(
            movieApi = get(),
            movieDao = get()
        )
    }
    single<MovieApi> {
        MovieApiImpl(
            httpClient = get(),
            context = androidContext()
        )
    }
    single {
        httpClient
    }
}
