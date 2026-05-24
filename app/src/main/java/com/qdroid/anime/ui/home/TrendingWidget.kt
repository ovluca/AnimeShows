package com.qdroid.anime.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import com.qdroid.anime.R
import com.qdroid.anime.domain.model.AnimeMovie
import com.qdroid.anime.ui.utils.CategoryHeader
import com.qdroid.anime.ui.utils.OnReachedEnd
import com.qdroid.anime.ui.utils.ScoreComposable
import com.qdroid.anime.ui.utils.titleTextStyle

@Composable
fun TrendingWidget(
    state: TrendingNowUiState,
    onLoadMore: () -> Unit,
    onNavigateToDetails: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    listState.OnReachedEnd(buffer = 3) {
        onLoadMore()
    }

    CategoryHeader(stringResource = R.string.trending_now)
    if (state.isLoading && state.trendingMovies.isEmpty()) {
        LinearProgressIndicator(Modifier.fillMaxWidth())
    }

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        items(
            count = state.trendingMovies.size,
            key = { state.trendingMovies[it].id }) {
            WidgetAnimeMovieItem(
                animeMovie = state.trendingMovies[it],
                onNavigateToDetails = onNavigateToDetails
            )
        }

        if (state.isLoading && state.trendingMovies.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun WidgetAnimeMovieItem(
    animeMovie: AnimeMovie,
    onNavigateToDetails: (Int) -> Unit = {}
) {
    Column(
        Modifier
            .width(dimensionResource(R.dimen.cover_image_width))
            .clickable { onNavigateToDetails(animeMovie.id) },
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        AsyncImage(
            modifier = Modifier
                .width(dimensionResource(R.dimen.cover_image_width))
                .height(dimensionResource(R.dimen.cover_image_height))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_small))),
            model = animeMovie.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = animeMovie.title
        )
        Text(
            text = animeMovie.title,
            style = titleTextStyle(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        ScoreComposable(score = animeMovie.score)
    }
}