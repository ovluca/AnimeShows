package com.qdroid.anime.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.qdroid.anime.R
import com.qdroid.anime.presentation.theme.light_purple
import com.qdroid.anime.presentation.theme.purple

@Composable
fun GenresComposable(genres: List<String>) {
    FlowRow(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_xsmall)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_xsmall))
    ) {
        genres.forEach { genre ->
            Text(
                modifier = Modifier
                    .background(
                        color = light_purple,
                        shape = RoundedCornerShape(size = dimensionResource(R.dimen.corner_radius_medium))
                    )
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_small),
                        vertical = dimensionResource(R.dimen.padding_xxsmall)
                    ),
                text = genre,
                style = genreTextStyle(),
                color = purple
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    GenresComposable(genres = listOf("Action", "Adventure", "Comedy"))
}