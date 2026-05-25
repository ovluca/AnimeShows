package com.qdroid.anime.domain.usecase

import com.qdroid.anime.domain.repository.AnimeRepository
import com.qdroid.anime.utility.DispatcherProvider
import kotlinx.coroutines.withContext

class GetAnimeMovieDetailsUseCase(
    private val repository: AnimeRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(id: Int) = withContext(dispatcherProvider.io) {
        repository.getAnimeDetails(id)
    }
}