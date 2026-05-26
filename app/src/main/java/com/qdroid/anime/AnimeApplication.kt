package com.qdroid.anime

import android.app.Application
import com.qdroid.anime.di.appModule
import com.qdroid.anime.di.repositoryModule
import com.qdroid.anime.di.useCaseModule
import com.qdroid.anime.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AnimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AnimeApplication)
            androidLogger()
            modules(appModule(), repositoryModule(), viewModelModule(), useCaseModule())
        }
    }
}