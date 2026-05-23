package com.qdroid.anime.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun titleTextStyle() = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp
)

@Composable
fun scoreTextStyle() = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)

@Composable
fun categoryTextStyle() = TextStyle(
    fontWeight = FontWeight.Black,
    fontSize = 16.sp
)

@Composable
fun smallButtonTextStyle() = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp
)

@Composable
fun topBarTitleTextStyle() = TextStyle(
    fontWeight = FontWeight.Black,
    fontSize = 16.sp
)