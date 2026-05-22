package com.qdroid.anime.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.qdroid.anime.utility.DefaultDispatcherProvider
import com.qdroid.anime.utility.DispatcherProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

fun appModule() = module {
    single {
        ApolloClient.Builder().serverUrl("https://graphql.anilist.co")
            .okHttpClient(okHttpClient = get())
            .build()
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    single<DispatcherProvider> { DefaultDispatcherProvider() }
}