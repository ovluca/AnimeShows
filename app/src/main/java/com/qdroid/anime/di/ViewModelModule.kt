package com.qdroid.anime.di

import com.qdroid.anime.ui.details.AnimeDetailsViewModel
import com.qdroid.anime.ui.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {

    viewModel {
        HomeViewModel(trendingNowUseCase = get(), popularNowUseCase = get())
    }

    viewModel {
        AnimeDetailsViewModel(getAnimeMovieDetailsUseCase = get())
    }
}