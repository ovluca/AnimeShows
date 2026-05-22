package com.qdroid.anime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.qdroid.anime.di.appModule
import com.qdroid.anime.di.repositoryModule
import com.qdroid.anime.di.useCaseModule
import com.qdroid.anime.di.viewModelModule
import com.qdroid.anime.presentation.theme.AnimeShowsTheme
import com.qdroid.anime.ui.HomeScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            androidLogger()
            modules(appModule(), repositoryModule(), viewModelModule(), useCaseModule())
        }

        enableEdgeToEdge()

        setContent {
            AnimeShowsTheme {
                HomeScreen()
            }
        }
    }
}