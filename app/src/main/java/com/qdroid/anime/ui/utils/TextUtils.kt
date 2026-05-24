package com.qdroid.anime.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun titleTextStyle() = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 19.sp
)

@Composable
fun scoreTextStyle() = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

@Composable
fun categoryTextStyle() = TextStyle(
    fontWeight = FontWeight.Black,
    fontSize = 21.sp
)

@Composable
fun smallButtonTextStyle() = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp
)

@Composable
fun topBarTitleTextStyle() = TextStyle(
    fontWeight = FontWeight.Black,
    fontSize = 21.sp
)

@Composable
fun genreTextStyle() = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 11.sp
)

@Composable
fun timeTextStyle() = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)