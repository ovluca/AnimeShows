package com.qdroid.anime.ui.home

sealed interface HomeScreenIntent {
    object LoadMoreTrending : HomeScreenIntent
    object LoadMorePopular : HomeScreenIntent
}