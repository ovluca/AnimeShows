package com.qdroid.anime.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.qdroid.anime.presentation.theme.background

@Composable
fun backgroundGradient() = Brush.horizontalGradient(
    0.0f to background,
    0.33f to background,
    0.33f to Color.White,
    1.0f to Color.White
)