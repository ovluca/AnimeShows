package com.qdroid.anime.domain.model

data class AnimeMovie(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val score: Int,
    val genres: List<String>,
    val duration: Int,
)
