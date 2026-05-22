package com.qdroid.anime.domain.repository

interface AnimeRepository {
    suspend fun getAnimeShows()
}