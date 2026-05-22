package com.qdroid.anime.domain.usecase

import com.qdroid.anime.domain.model.AnimeMovie
import com.qdroid.anime.domain.repository.AnimeRepository
import com.qdroid.anime.utility.DispatcherProvider
import kotlinx.coroutines.withContext

class TrendingNowUseCase(
    val repository: AnimeRepository,
    val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(): Result<List<AnimeMovie>> = withContext(dispatcherProvider.io) {
        return@withContext repository.getAnimeShows()
    }
}