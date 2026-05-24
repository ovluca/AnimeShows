package com.qdroid.anime.ui.home

sealed interface HomeEvents {
    data class OnError(val errorMsg: String) : HomeEvents
}