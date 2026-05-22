package com.qdroid.anime.domain.repository

import com.qdroid.anime.domain.model.AnimeMovie

interface AnimeRepository {
    suspend fun getAnimeShows(): Result<List<AnimeMovie>>
}