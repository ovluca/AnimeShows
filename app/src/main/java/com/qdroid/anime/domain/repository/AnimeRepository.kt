package com.qdroid.anime.domain.repository

import com.qdroid.anime.domain.model.AnimeRequestType
import com.qdroid.anime.domain.model.PaginatedMovies

interface AnimeRepository {
    suspend fun getAnimeShows(requestType: AnimeRequestType, page: Int): Result<PaginatedMovies>
}