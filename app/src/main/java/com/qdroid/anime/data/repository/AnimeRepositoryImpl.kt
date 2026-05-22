package com.qdroid.anime.data.repository

import com.apollographql.apollo.ApolloClient
import com.qdroid.anime.TrendingNowQuery
import com.qdroid.anime.domain.model.AnimeMovie
import com.qdroid.anime.domain.repository.AnimeRepository

class AnimeRepositoryImpl(val client: ApolloClient) :
    AnimeRepository {

    override suspend fun getAnimeShows(): Result<List<AnimeMovie>> {
        val response = client.query(TrendingNowQuery(1, 10)).execute()

        if (response.hasErrors()) {
            return Result.failure(Exception(response.errors?.first()?.message))
        }

        return Result.success(response.data?.Page?.media?.map {
            AnimeMovie(
                title = it?.title?.english.orEmpty(),
                imageUrl = it?.coverImage?.large.orEmpty(),
                score = it?.averageScore ?: 0,
                genres = it?.genres?.map { genre -> genre.orEmpty() } ?: emptyList()
            )
        } ?: emptyList())
    }
}