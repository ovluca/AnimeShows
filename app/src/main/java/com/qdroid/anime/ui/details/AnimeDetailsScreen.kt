package com.qdroid.anime.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.qdroid.anime.R
import com.qdroid.anime.domain.model.Character
import com.qdroid.anime.presentation.theme.dark_blue
import com.qdroid.anime.presentation.theme.gray
import com.qdroid.anime.ui.utils.CategoryHeader
import com.qdroid.anime.ui.utils.GenresComposable
import com.qdroid.anime.ui.utils.ScoreComposable
import com.qdroid.anime.ui.utils.descriptionHeaderTextStyle
import com.qdroid.anime.ui.utils.descriptionTextStyle
import com.qdroid.anime.ui.utils.titleHeaderTextStyle
import org.koin.androidx.compose.koinViewModel


@Composable
fun AnimeDetailsScreen(viewModel: AnimeDetailsViewModel = koinViewModel(), id: Int) {

    LaunchedEffect(id) {
        viewModel.onIntent(AnimeDetailsIntent.LoadDetails(id))
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScreenContent(
        uiState = uiState,
        onNavigateBack = { viewModel.onIntent(AnimeDetailsIntent.NavigateBack) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(uiState: AnimeDetailsUiState, onNavigateBack: () -> Unit = {}) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {

                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { _ ->
        Box(modifier = Modifier.fillMaxSize()) {

            uiState.animeMovie?.let {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    model = it.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            LazyColumn(
                modifier = Modifier
                    .padding(top = 350.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = dimensionResource(R.dimen.corner_radius_medium),
                            topEnd = dimensionResource(R.dimen.corner_radius_medium)
                        )
                    )
                    .background(Color.White),
                contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                item {
                    if (uiState.isLoading) {
                        CircularProgressIndicator()
                    }
                }

                item {
                    Text(text = uiState.animeMovie?.title ?: "", style = titleHeaderTextStyle())
                }

                item { ScoreComposable(uiState.animeMovie?.score ?: 0) }

                item { GenresComposable(uiState.animeMovie?.genres ?: emptyList()) }

                item {
                    DescriptionComposable(uiState.animeMovie?.description ?: "")
                }

                item {
                    CharactersWidgetComposable(uiState.animeMovie?.characters ?: emptyList())
                }

            }
        }
    }
}

@Composable
private fun DescriptionComposable(description: String) {
    Text(
        text = stringResource(R.string.description),
        style = descriptionHeaderTextStyle(),
        color = dark_blue
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_xxsmall)))
    val annotatedText = remember(description) {
        val spanned = HtmlCompat.fromHtml(
            description,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        val text = spanned.toString()
        buildAnnotatedString {
            append(text)
        }
    }
    Text(
        text = annotatedText,
        style = descriptionTextStyle(),
        color = gray
    )
}

@Composable
private fun CharactersWidgetComposable(characters: List<Character>) {
    CategoryHeader(
        stringResource = R.string.characters,
        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
    )
    LazyRow(
        state = rememberLazyListState(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_xsmall)),
    ) {
        items(count = characters.size) { index ->
            Column(
                modifier = Modifier.width(dimensionResource(R.dimen.cover_image_width_xsmall)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_xxsmall))
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.cover_image_width_xsmall))
                        .height(dimensionResource(R.dimen.cover_image_height_xsmall))
                        .clip(shape = RoundedCornerShape(size = dimensionResource(R.dimen.corner_radius_small))),
                    model = characters[index].imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = characters[index].name,
                    style = descriptionTextStyle()
                )
            }
        }
    }
}