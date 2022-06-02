package com.example.tmdb

import android.app.Application
import com.example.tmdb.di.modules.dataModule
import com.example.tmdb.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TmdbApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TmdbApplication)
            modules(
                viewModelModule,
                dataModule
            )
        }
    }
}
