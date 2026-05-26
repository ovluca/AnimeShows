package com.qdroid.anime.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.qdroid.anime.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar() {
    TopAppBar(
        modifier = Modifier.background(
            brush = backgroundGradient(),
        ),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent), title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size_big)),
                        painter = painterResource(R.drawable.ic_home_menu),
                        contentDescription = "",
                        tint = Color.Unspecified
                    )
                }
                Text(text = stringResource(R.string.app_name), style = topBarTitleTextStyle())
                IconButton(onClick = {}) {
                    Icon(
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size_big)),
                        painter = painterResource(R.drawable.ic_notifications),
                        contentDescription = "",
                        tint = Color.Unspecified
                    )
                }
            }
        })

}

@Composable
@Preview
private fun HomeTopBarApp() {
    HomeTopBar()
}