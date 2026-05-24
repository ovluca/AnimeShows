package com.qdroid.anime.ui.home

import com.qdroid.anime.domain.model.AnimeMovie

data class HomeUiState(
    val trendingMovies: List<AnimeMovie> = emptyList(),
    val popularMovies: List<AnimeMovie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)