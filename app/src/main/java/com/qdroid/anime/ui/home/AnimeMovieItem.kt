package com.qdroid.anime.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.qdroid.anime.ui.utils.DurationComposable
import com.qdroid.anime.ui.utils.GenresComposable
import com.qdroid.anime.ui.utils.ScoreComposable
import com.qdroid.anime.ui.utils.titleTextStyle

@Composable
fun AnimeMovieItem(movie: AnimeMovie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        AsyncImage(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.cover_image_width_small))
                .height(dimensionResource(id = R.dimen.cover_image_height_small))
                .clip(
                    shape = RoundedCornerShape(size = dimensionResource(R.dimen.corner_radius_small))
                ),
            model = movie.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = movie.title
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_xsmall))
        ) {
            Text(
                text = movie.title,
                style = titleTextStyle(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            ScoreComposable(movie.score)
            GenresComposable(movie.genres)
            DurationComposable(movie.duration)
        }
    }
}