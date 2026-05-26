package com.qdroid.anime.ui.details

sealed interface AnimeDetailsEvents {
    data class OnError(val errorMsg: String) : AnimeDetailsEvents
}