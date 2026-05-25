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
import com.qdroid.anime.ui.navigation.MainNavigation
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AnimeShowsTheme {
                MainNavigation()
            }
        }
    }
}