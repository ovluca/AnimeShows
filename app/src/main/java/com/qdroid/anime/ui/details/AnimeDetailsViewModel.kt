package com.qdroid.anime.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qdroid.anime.domain.usecase.GetAnimeMovieDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnimeDetailsViewModel(
    private val getAnimeMovieDetailsUseCase: GetAnimeMovieDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnimeDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun onIntent(intent: AnimeDetailsIntent) {
        when (intent) {
            is AnimeDetailsIntent.LoadDetails -> {
                _uiState.value = _uiState.value.copy(isLoading = true, animeMovie = null)
                viewModelScope.launch {
                    getAnimeMovieDetailsUseCase(intent.id).onSuccess { animeMovie ->
                        _uiState.update { it.copy(isLoading = false, animeMovie = animeMovie) }
                    }.onFailure { }
                }
            }

            AnimeDetailsIntent.NavigateBack -> TODO()
        }
    }
}