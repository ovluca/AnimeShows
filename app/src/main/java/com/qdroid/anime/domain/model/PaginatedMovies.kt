package com.qdroid.anime.domain.model

data class PaginatedMovies(
    val currentPage: Int = 1,
    val hasNextPage: Boolean = false,
    val movies: List<AnimeMovie> = emptyList()
)