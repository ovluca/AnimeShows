package com.qdroid.anime.data.repository

import com.apollographql.apollo.ApolloClient
import com.qdroid.anime.PopularNowQuery
import com.qdroid.anime.TrendingNowQuery
import com.qdroid.anime.domain.model.AnimeMovie
import com.qdroid.anime.domain.model.AnimeRequestType
import com.qdroid.anime.domain.repository.AnimeRepository

class AnimeRepositoryImpl(val client: ApolloClient) :
    AnimeRepository {

    override suspend fun getAnimeShows(requestType: AnimeRequestType): Result<List<AnimeMovie>> {
        val response = when (requestType) {
            AnimeRequestType.Trending -> client.query(TrendingNowQuery(page = 1, perPage = 10))
                .execute()

            AnimeRequestType.Popularity -> client.query(PopularNowQuery(page = 1, perPage = 10))
                .execute()
        }

        if (response.hasErrors()) {
            return Result.failure(Exception(response.errors?.first()?.message))
        }

        val animeMovies = when (val data = response.data) {
            is TrendingNowQuery.Data -> data.Page?.media?.mapNotNull { it?.toAnimeMovie() }
            is PopularNowQuery.Data -> data.Page?.media?.mapNotNull { it?.toAnimeMovie() }
            else -> null
        }

        return Result.success(animeMovies ?: emptyList())
    }

    private fun TrendingNowQuery.Medium.toAnimeMovie() = AnimeMovie(
        title = title?.english ?: title?.romaji.orEmpty(),
        imageUrl = coverImage?.large.orEmpty(),
        score = averageScore ?: 0,
        genres = emptyList(),
        duration = 0,
        id = id
    )

    private fun PopularNowQuery.Medium.toAnimeMovie() = AnimeMovie(
        title = title?.english ?: title?.romaji.orEmpty(),
        imageUrl = coverImage?.large.orEmpty(),
        score = averageScore ?: 0,
        genres = genres?.filterNotNull() ?: emptyList(),
        duration = duration ?: 0,
        id = id
    )
}