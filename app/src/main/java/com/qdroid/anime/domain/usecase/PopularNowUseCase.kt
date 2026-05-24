package com.qdroid.anime.domain.usecase

import com.qdroid.anime.domain.model.AnimeRequestType
import com.qdroid.anime.domain.model.PaginatedMovies
import com.qdroid.anime.domain.repository.AnimeRepository
import com.qdroid.anime.utility.DispatcherProvider
import kotlinx.coroutines.withContext

class PopularNowUseCase(
    val repository: AnimeRepository,
    val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(page: Int): Result<PaginatedMovies> =
        withContext(dispatcherProvider.io) {
            return@withContext repository.getAnimeShows(AnimeRequestType.Popularity, page = page)
    }
}