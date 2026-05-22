package com.qdroid.anime.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.qdroid.anime.domain.model.AnimeMovie
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { contentPadding ->
        HomeScreenContent(state = state, paddingValues = contentPadding)
    }
}

@Composable
private fun HomeScreenContent(state: HomeUiState, paddingValues: PaddingValues = PaddingValues()) {
    Column(modifier = Modifier.padding(paddingValues)) {
        LazyRow(state = rememberLazyListState()) {
            items(
                count = state.animeMovies.size,
                key = { state.animeMovies[it].title.hashCode() }) {
                AnimeMovieItem(state.animeMovies[it])
            }
        }
    }
}

@Composable
private fun AnimeMovieItem(animeMovie: AnimeMovie) {
    Column {
        AsyncImage(model = animeMovie.imageUrl, contentDescription = animeMovie.title)
        Text(text = animeMovie.title)
        Text(text = animeMovie.score.toString())
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen()
}