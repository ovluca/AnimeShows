package com.qdroid.anime.ui.details

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import coil3.compose.AsyncImage
import com.qdroid.anime.R
import com.qdroid.anime.domain.model.AnimeMovieDetails
import com.qdroid.anime.domain.model.Character
import com.qdroid.anime.presentation.theme.dark_blue
import com.qdroid.anime.presentation.theme.gray
import com.qdroid.anime.ui.utils.CategoryHeader
import com.qdroid.anime.ui.utils.GenresComposable
import com.qdroid.anime.ui.utils.ScoreComposable
import com.qdroid.anime.ui.utils.descriptionHeaderTextStyle
import com.qdroid.anime.ui.utils.descriptionTextStyle
import com.qdroid.anime.ui.utils.mediumBoldTextStyle
import com.qdroid.anime.ui.utils.mediumNormalTextStyle
import com.qdroid.anime.ui.utils.mediumSemiBoldTextStyle
import com.qdroid.anime.ui.utils.titleHeaderTextStyle
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailsScreen(
    viewModel: AnimeDetailsViewModel = koinViewModel(),
    id: Int,
    onNavigateBack: () -> Unit = {}
) {

    LaunchedEffect(id) {
        viewModel.onIntent(AnimeDetailsIntent.LoadDetails(id))
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.eventsFlow, lifecycleOwner) {
        viewModel.eventsFlow
            .flowWithLifecycle(
                lifecycle = lifecycleOwner.lifecycle,
                minActiveState = Lifecycle.State.STARTED
            )
            .collect { event ->
                when (event) {
                    is AnimeDetailsEvents.OnError -> {
                        snackbarHostState.showSnackbar(
                            message = event.errorMsg,
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            }
    }

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
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_menu),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { _ ->
        ScreenContent(
            uiState = uiState,
            onNavigateBack = onNavigateBack
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    uiState: AnimeDetailsUiState,
    onNavigateBack: () -> Unit = {}
) {


    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {

        uiState.animeMovie?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.trailer_image_height))
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.trailer_image_height))
                        .clickable {
                            if (it.trailerUrl.isEmpty()) return@clickable
                            val intent = Intent(Intent.ACTION_VIEW, it.trailerUrl.toUri())
                            context.startActivity(intent)
                        },
                    model = it.trailerThumbnail.ifEmpty { it.imageUrl },
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                if (it.trailerUrl.isEmpty()) return@Box

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_xsmall))
                ) {
                    Image(
                        painter = painterResource(R.drawable.button_play),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.play_trailer),
                        color = Color.White,
                        style = mediumBoldTextStyle()
                    )
                }
            }
        }

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Box
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = uiState.animeMovie?.title ?: "", style = titleHeaderTextStyle())
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.ic_bookmark),
                            contentDescription = null,
                            tint = gray
                        )
                    }
                }
                ScoreComposable(uiState.animeMovie?.score ?: 0)
            }

            item { GenresComposable(uiState.animeMovie?.genres ?: emptyList()) }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_xxsmall))) {
                    Text(
                        text = stringResource(R.string.length),
                        style = mediumNormalTextStyle(),
                        color = gray
                    )
                    Text(
                        text = "${uiState.animeMovie?.duration}m",
                        style = mediumSemiBoldTextStyle(),
                        color = Color.Black
                    )
                }
            }

            item {
                DescriptionComposable(uiState.animeMovie?.description ?: "")
            }

            item {
                CharactersWidgetComposable(uiState.animeMovie?.characters ?: emptyList())
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

@Preview(showBackground = true)
@Composable
fun AnimeDetailsPreview() {
    ScreenContent(
        uiState = AnimeDetailsUiState(
            isLoading = false,
            animeMovie = AnimeMovieDetails(
                id = 1,
                title = "Naruto Shippuden",
                imageUrl = "https://example.com/image.jpg",
                score = 82,
                genres = listOf("Action", "Adventure", "Fantasy"),
                duration = 24,
                description = "Naruto Uzumaki, is a loud, hyperactive, adolescent ninja who constantly searches for approval and recognition, as well as to become Hokage, who is acknowledged as the leader and strongest of all ninja in the village.",
                trailerUrl = "",
                trailerThumbnail = "",
                characters = listOf(
                    Character("Naruto Uzumaki", ""),
                    Character("Sasuke Uchiha", ""),
                    Character("Sakura Haruno", "")
                )
            )
        ),
    )
}