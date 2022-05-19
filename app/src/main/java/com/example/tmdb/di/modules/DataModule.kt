package com.example.tmdb.di.modules

import com.example.tmdb.data.*
import com.example.tmdb.data.MovieApiImpl
import com.example.tmdb.data.MovieRepositoryImpl
import org.koin.dsl.module

val dataModule = module {

    single<MovieRepository> {
        MovieRepositoryImpl(
            movieApi = get(),
            movieDatabase = get()
        )
    }
    single<MovieApi> {
        MovieApiImpl(httpClient = get())
    }
    single<MovieDatabase> {
        MovieDatabaseImpl()
    }
    single {
        httpClient
    }
}
