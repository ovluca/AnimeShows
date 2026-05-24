package com.qdroid.anime.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qdroid.anime.domain.usecase.PopularNowUseCase
import com.qdroid.anime.domain.usecase.TrendingNowUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    val trendingNowUseCase: TrendingNowUseCase,
    val popularNowUseCase: PopularNowUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {

        viewModelScope.launch {
            trendingNowUseCase().onSuccess { movies ->
                _uiState.update { it.copy(trendingMovies = movies) }
            }.onFailure { error ->
                _uiState.update { it.copy(error = error.message) }
            }
        }

        viewModelScope.launch {
            popularNowUseCase().onSuccess { movies ->
                _uiState.update { it.copy(popularMovies = movies) }
            }.onFailure { error ->
                _uiState.update { it.copy(error = error.message) }
            }
        }
    }
}