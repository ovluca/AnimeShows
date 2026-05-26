package com.qdroid.anime.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qdroid.anime.domain.usecase.PopularNowUseCase
import com.qdroid.anime.domain.usecase.TrendingNowUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val trendingNowUseCase: TrendingNowUseCase,
    private val popularNowUseCase: PopularNowUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<HomeEvents>(extraBufferCapacity = 1)
    val eventsFlow = _events.asSharedFlow()

    init {
        loadTrendingMovies()
        loadPopularMovies()
    }

    private fun loadTrendingMovies(page: Int = 1) {
        _uiState.update { it.copy(trendingNow = it.trendingNow.copy(isLoading = true)) }
        viewModelScope.launch {
            trendingNowUseCase(page = page).onSuccess { newData ->
                _uiState.update {
                    it.copy(
                        trendingNow = it.trendingNow.copy(
                            currentPage = newData.currentPage,
                            hasNextPage = newData.hasNextPage,
                            trendingMovies = it.trendingNow.trendingMovies + newData.movies,
                            isLoading = false
                        )
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(trendingNow = it.trendingNow.copy(isLoading = false))
                }
                _events.emit(HomeEvents.OnError(error.message ?: "Unknown error"))
            }
        }
    }

    private fun loadPopularMovies(page: Int = 1) {
        _uiState.update { it.copy(popularNow = it.popularNow.copy(isLoading = true)) }
        viewModelScope.launch {
            popularNowUseCase(page = page).onSuccess { newData ->
                _uiState.update {
                    it.copy(
                        popularNow = it.popularNow.copy(
                            popularMovies = it.popularNow.popularMovies + newData.movies,
                            isLoading = false,
                            currentPage = newData.currentPage,
                            hasNextPage = newData.hasNextPage
                        )
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(popularNow = it.popularNow.copy(isLoading = false))
                }
                _events.emit(HomeEvents.OnError(error.message ?: "Unknown error"))
            }
        }
    }

    private fun loadNextTrendingPage() {
        val state = _uiState.value.trendingNow
        if (!state.hasNextPage || state.isLoading) return
        loadTrendingMovies(page = state.currentPage + 1)
    }

    private fun loadNextPopularPage() {
        val state = _uiState.value.popularNow
        if (!state.hasNextPage || state.isLoading) return
        loadPopularMovies(page = state.currentPage + 1)
    }

    fun onIntent(intent: HomeScreenIntent) {
        when (intent) {
            is HomeScreenIntent.LoadMoreTrending -> loadNextTrendingPage()
            is HomeScreenIntent.LoadMorePopular -> loadNextPopularPage()
        }
    }
}