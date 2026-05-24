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

@Composable
fun DurationComposable(duration: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_xxsmall)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(dimensionResource(R.dimen.icon_size)),
            painter = painterResource(R.drawable.ic_time),
            contentDescription = ""
        )
        Text(
            text = "${duration}m",
            style = timeTextStyle(),
            color = Color.Black
        )
    }
}

@Composable
@Preview
private fun Preview() {
    DurationComposable(120)
}