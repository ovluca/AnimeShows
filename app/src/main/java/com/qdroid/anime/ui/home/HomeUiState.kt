package com.qdroid.anime.ui.home

import com.qdroid.anime.domain.model.AnimeMovie

data class HomeUiState(
    val trendingNow: TrendingNowUiState = TrendingNowUiState(),
    val popularNow: PopularNowUiState = PopularNowUiState(),
    val errorMsg: String? = null
)

data class TrendingNowUiState(
    val trendingMovies: List<AnimeMovie> = emptyList(),
    val isLoading: Boolean = true,
    val hasNextPage: Boolean = false,
    val currentPage: Int = 1,
)

data class PopularNowUiState(
    val popularMovies: List<AnimeMovie> = emptyList(),
    val isLoading: Boolean = true,
    val hasNextPage: Boolean = false,
    val currentPage: Int = 1,
)