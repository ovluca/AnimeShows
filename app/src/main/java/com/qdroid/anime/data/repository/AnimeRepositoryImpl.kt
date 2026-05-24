package com.qdroid.anime.data.repository

import com.apollographql.apollo.ApolloClient
import com.qdroid.anime.PopularNowQuery
import com.qdroid.anime.TrendingNowQuery
import com.qdroid.anime.domain.model.AnimeMovie
import com.qdroid.anime.domain.model.AnimeRequestType
import com.qdroid.anime.domain.model.PaginatedMovies
import com.qdroid.anime.domain.repository.AnimeRepository

private const val PER_PAGE = 20

class AnimeRepositoryImpl(private val client: ApolloClient) : AnimeRepository {

    override suspend fun getAnimeShows(
        requestType: AnimeRequestType,
        page: Int
    ): Result<PaginatedMovies> {

        val response = try {
            when (requestType) {
                AnimeRequestType.Trending -> client.query(
                    TrendingNowQuery(
                        page = page,
                        perPage = PER_PAGE
                    )
                ).execute()

                AnimeRequestType.Popularity -> client.query(
                    PopularNowQuery(
                        page = page,
                        perPage = PER_PAGE
                    )
                ).execute()
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }

        response.exception?.let { return Result.failure(it) }

        val data = response.data
        val errors = response.errors

        val animeMovies = when (val data = response.data) {
            is TrendingNowQuery.Data -> PaginatedMovies(
                currentPage = data.Page?.pageInfo?.currentPage ?: 1,
                hasNextPage = data.Page?.pageInfo?.hasNextPage ?: false,
                movies = data.Page?.media?.mapNotNull { it?.toAnimeMovie() }.orEmpty()
            )

            is PopularNowQuery.Data -> PaginatedMovies(
                currentPage = data.Page?.pageInfo?.currentPage ?: 1,
                hasNextPage = data.Page?.pageInfo?.hasNextPage ?: false,
                movies = data.Page?.media?.mapNotNull { it?.toAnimeMovie() }.orEmpty()
            )

            else -> PaginatedMovies()
        }

        return when {
            data != null && errors?.isEmpty() == true -> Result.success(animeMovies)
            data != null && errors?.isNotEmpty() == true -> Result.success(animeMovies)
            errors?.isNotEmpty() == true -> Result.failure(Exception(errors.joinToString(separator = "\n") { it.message }))
            else -> Result.failure(Exception("Unknown error: no data/errors"))
        }

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