package com.qdroid.anime.di

import com.qdroid.anime.data.repository.AnimeRepositoryImpl
import com.qdroid.anime.domain.repository.AnimeRepository
import org.koin.dsl.module

fun repositoryModule() = module {
    single<AnimeRepository> { AnimeRepositoryImpl(get(), get()) }
}