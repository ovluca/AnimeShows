package com.qdroid.anime.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import com.qdroid.anime.R
import com.qdroid.anime.domain.model.AnimeMovie
import com.qdroid.anime.ui.utils.CategoryHeader
import com.qdroid.anime.ui.utils.ScoreComposable
import com.qdroid.anime.ui.utils.titleTextStyle

@Composable
fun TrendingWidget(trendingMovies: List<AnimeMovie>) {
    CategoryHeader(stringResource = R.string.trending_now)
    LazyRow(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        items(
            count = trendingMovies.size,
            key = { trendingMovies[it].id }) {
            WidgetAnimeMovieItem(trendingMovies[it])
        }
    }
}

@Composable
private fun WidgetAnimeMovieItem(animeMovie: AnimeMovie) {
    Column(
        Modifier.width(dimensionResource(R.dimen.cover_image_width)),
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