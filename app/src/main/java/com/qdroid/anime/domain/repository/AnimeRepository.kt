package com.qdroid.anime.domain.repository

import com.qdroid.anime.domain.model.AnimeMovie
import com.qdroid.anime.domain.model.AnimeRequestType

interface AnimeRepository {
    suspend fun getAnimeShows(requestType: AnimeRequestType): Result<List<AnimeMovie>>
}