package com.qdroid.anime.ui.details

sealed interface AnimeDetailsIntent {
    data class LoadDetails(val id: Int) : AnimeDetailsIntent
}