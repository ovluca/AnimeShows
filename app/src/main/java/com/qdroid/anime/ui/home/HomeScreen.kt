package com.qdroid.anime.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qdroid.anime.R
import com.qdroid.anime.domain.model.AnimeMovie
import com.qdroid.anime.ui.utils.CategoryHeader
import com.qdroid.anime.ui.utils.HomeTopBar
import com.qdroid.anime.ui.utils.backgroundGradient
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            HomeTopBar()
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = backgroundGradient())
        ) {
            HomeScreenContent(state = state, paddingValues = contentPadding)
        }
    }
}

@Composable
private fun HomeScreenContent(state: HomeUiState, paddingValues: PaddingValues = PaddingValues()) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            item { TrendingWidget(state.trendingMovies) }
            item {
                CategoryHeader(
                    modifier = Modifier.offset(y = dimensionResource(R.dimen.padding_medium)),
                    stringResource = R.string.popular_this_season
                )
            }
            items(
                count = state.popularMovies.size,
                key = { state.popularMovies[it].id }) {
                val movie = state.popularMovies[it]
                AnimeMovieItem(movie)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreenContent(
        state = HomeUiState(
            trendingMovies = listOf(
                AnimeMovie(
                    title = "Naruto",
                    imageUrl = "",
                    score = 94,
                    genres = emptyList(),
                    duration = 0,
                    id = 1
                )
            ),
            popularMovies = listOf(
                AnimeMovie(
                    title = "Naruto Shippuden",
                    imageUrl = "",
                    score = 94,
                    genres = listOf("Action", "Comedy"),
                    duration = 25,
                    id = 2
                )
            )
        )
    )
}