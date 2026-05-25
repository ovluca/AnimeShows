package com.qdroid.anime.ui.details

import com.qdroid.anime.domain.model.AnimeMovieDetails

data class AnimeDetailsUiState(
    val isLoading: Boolean = true,
    val animeMovie: AnimeMovieDetails? = null,
)
