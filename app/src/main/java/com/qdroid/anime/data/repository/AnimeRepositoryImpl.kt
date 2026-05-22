package com.qdroid.anime.data.repository

import com.apollographql.apollo.ApolloClient
import com.qdroid.anime.TrendingNowQuery
import com.qdroid.anime.domain.repository.AnimeRepository
import com.qdroid.anime.utility.DispatcherProvider
import kotlinx.coroutines.withContext

class AnimeRepositoryImpl(val client: ApolloClient, val dispatcherProvider: DispatcherProvider) :
    AnimeRepository {

    override suspend fun getAnimeShows() {
        withContext(dispatcherProvider.io) {
            val response = client.query(TrendingNowQuery(1, 10)).execute()
            response.data?.Page?.media
        }
    }
}