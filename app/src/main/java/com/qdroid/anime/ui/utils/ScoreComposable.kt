package com.qdroid.anime.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.qdroid.anime.R
import com.qdroid.anime.presentation.theme.gray

@Composable
fun ScoreComposable(score: Int) {
    val iconRes = when {
        score <= 60 -> {
            R.drawable.ic_sad
        }

        score in 61..75 -> {
            R.drawable.ic_neutral
        }

        else -> {
            R.drawable.ic_satisfied
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_xxsmall))
    ) {
        Icon(
            modifier = Modifier.size(dimensionResource(R.dimen.icon_size)),
            painter = painterResource(iconRes),
            contentDescription = "",
            tint = Color.Unspecified
        )
        Text(text = "$score%", style = scoreTextStyle(), color = gray)
    }
}

@Composable
@Preview
private fun Preview() {
    ScoreComposable(90)
}
