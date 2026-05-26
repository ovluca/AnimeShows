package com.qdroid.anime.data.repository

import com.apollographql.apollo.ApolloClient
import com.qdroid.anime.AnimeDetailsQuery
import com.qdroid.anime.PopularNowQuery
import com.qdroid.anime.TrendingNowQuery
import com.qdroid.anime.domain.model.AnimeMovie
import com.qdroid.anime.domain.model.AnimeMovieDetails
import com.qdroid.anime.domain.model.AnimeRequestType
import com.qdroid.anime.domain.model.Character
import com.qdroid.anime.domain.model.PaginatedMovies
import com.qdroid.anime.domain.repository.AnimeRepository

private const val PER_PAGE = 20
private const val NO_DATA = "Error fetching data"

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

        val errors = response.errors
        if (errors?.isNotEmpty() == true) {
            return Result.failure(Exception(errors.joinToString(separator = "\n") { it.message }))
        }

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
        return Result.success(animeMovies)
    }

    override suspend fun getAnimeDetails(id: Int): Result<AnimeMovieDetails> {
        val response = try {
            client.query(AnimeDetailsQuery(id)).execute()
        } catch (e: Exception) {
            return Result.failure(e)
        }

        response.exception?.let { return Result.failure(it) }

        val errors = response.errors
        if (errors?.isNotEmpty() == true) {
            return Result.failure(Exception(errors.joinToString(separator = "\n") { it.message }))
        }

        val data = response.data ?: return Result.failure(Exception(NO_DATA))
        val animeDetails = data.Media?.toAnimeMovie()
            ?: return Result.failure(Exception(NO_DATA))

        return Result.success(animeDetails)
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

    private fun AnimeDetailsQuery.Media.toAnimeMovie() =
        AnimeMovieDetails(
            title = title?.english ?: title?.romaji.orEmpty(),
            imageUrl = coverImage?.extraLarge.orEmpty(),
            score = averageScore ?: 0,
            genres = genres?.filterNotNull() ?: emptyList(),
            duration = duration ?: 0,
            id = id,
            description = description.orEmpty(),
            trailerUrl = if (trailer != null) "https://www.youtube.com/watch?v=" + trailer.id else "",
            trailerThumbnail = trailer?.thumbnail.orEmpty(),
            characters = characters?.nodes?.map {
                Character(
                    name = it?.name?.full.orEmpty(), imageUrl = it?.image?.medium.orEmpty()
                )
            } ?: emptyList())

}