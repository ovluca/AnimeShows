package com.qdroid.anime.ui

import com.qdroid.anime.domain.model.AnimeMovie

data class HomeUiState(
    val animeMovies: List<AnimeMovie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)