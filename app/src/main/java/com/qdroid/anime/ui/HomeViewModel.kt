package com.qdroid.anime.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qdroid.anime.domain.usecase.TrendingNowUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(val trendingNowUseCase: TrendingNowUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            trendingNowUseCase().onSuccess { _uiState.value = HomeUiState(animeMovies = it) }
                .onFailure { _uiState.value = HomeUiState(error = it.message) }
        }
    }
}