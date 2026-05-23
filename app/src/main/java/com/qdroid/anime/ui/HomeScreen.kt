package com.qdroid.anime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.qdroid.anime.R
import com.qdroid.anime.domain.model.AnimeMovie
import com.qdroid.anime.presentation.theme.gray
import com.qdroid.anime.ui.utils.HomeTopBar
import com.qdroid.anime.ui.utils.ScoreComposable
import com.qdroid.anime.ui.utils.SeeMoreButton
import com.qdroid.anime.ui.utils.categoryTextStyle
import com.qdroid.anime.ui.utils.scoreTextStyle
import com.qdroid.anime.ui.utils.titleTextStyle
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            HomeTopBar()
        }
    )
    { contentPadding ->
        HomeScreenContent(state = state, paddingValues = contentPadding)
    }
}

@Composable
private fun HomeScreenContent(state: HomeUiState, paddingValues: PaddingValues = PaddingValues()) {
    Column(modifier = Modifier.padding(paddingValues)) {
        CategoryHeader()
        LazyRow(
            state = rememberLazyListState(),
            contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            items(
                count = state.animeMovies.size,
                key = { state.animeMovies[it].title.hashCode() }) {
                AnimeMovieItem(state.animeMovies[it])
            }
        }
    }
}

@Composable
private fun CategoryHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium), vertical = 1.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.trending_now), style = categoryTextStyle())
        SeeMoreButton {

        }
    }
}

@Composable
private fun AnimeMovieItem(animeMovie: AnimeMovie) {
    Column(
        Modifier.width(dimensionResource(R.dimen.cover_image_width)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        AsyncImage(
            modifier = Modifier
                .width(dimensionResource(R.dimen.cover_image_width))
                .height(dimensionResource(R.dimen.cover_image_height))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium))),
            model = animeMovie.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = animeMovie.title
        )
        Text(
            text = animeMovie.title,
            style = titleTextStyle(),
            maxLines = 2,
            minLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        ScoreComposable(score = animeMovie.score)
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreenContent(
        state = HomeUiState(
            animeMovies = listOf(
                AnimeMovie(
                    title = "Naruto",
                    imageUrl = "",
                    score = 94,
                    genres = emptyList()
                )
            )
        )
    )
}