package com.qdroid.anime.di

import com.qdroid.anime.domain.usecase.TrendingNowUseCase
import org.koin.dsl.module

fun useCaseModule() = module {
    single { TrendingNowUseCase(repository = get(), dispatcherProvider = get()) }
}