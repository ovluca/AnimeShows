package com.qdroid.anime.domain.model

data class AnimeMovieDetails(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val score: Int,
    val genres: List<String>,
    val duration: Int,
    val description: String,
    val trailerUrl: String,
    val trailerThumbnail: String,
    val characters: List<Character>
)