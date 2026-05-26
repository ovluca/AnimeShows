package com.qdroid.anime.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qdroid.anime.R
import com.qdroid.anime.presentation.theme.light_gray
import com.qdroid.anime.presentation.theme.medium_gray

@Composable
fun SeeMoreButton(onClick: () -> Unit) = OutlinedButton(
    onClick = onClick,
    contentPadding = PaddingValues(
        horizontal = dimensionResource(R.dimen.padding_xsmall),
        vertical = dimensionResource(R.dimen.padding_xxsmall)
    ),
    border = BorderStroke(1.dp, light_gray),
    colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.White),
    modifier = Modifier.defaultMinSize(1.dp, 1.dp)
) {
    Text(
        text = stringResource(R.string.see_more),
        style = smallButtonTextStyle(),
        color = medium_gray
    )
}

@Composable
@Preview
private fun SeeMoreButtonPreview() {
    SeeMoreButton { }
}