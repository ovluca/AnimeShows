package com.qdroid.anime.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.qdroid.anime.R

val MulishFontFamily = FontFamily(
    Font(R.font.mulish_regular, FontWeight.Normal),
    Font(R.font.mulish_bold, FontWeight.Bold),
    Font(R.font.mulish_semibold, FontWeight.SemiBold),
)

val merriweatherFontFamily = FontFamily(
    Font(R.font.merriweather_96pt_black, FontWeight.Black),
)
@Composable
fun titleTextStyle() = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 19.sp
)

@Composable
fun descriptionTextStyle() = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

@Composable
fun titleHeaderTextStyle() = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 27.sp
)

@Composable
fun descriptionHeaderTextStyle() = TextStyle(
    fontWeight = FontWeight.Black,
    fontSize = 27.sp
)

@Composable
fun scoreTextStyle() = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

@Composable
fun categoryTextStyle() = TextStyle(
    fontFamily = merriweatherFontFamily,
    fontWeight = FontWeight.Black,
    fontSize = 21.sp
)

@Composable
fun smallButtonTextStyle() = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp
)

@Composable
fun topBarTitleTextStyle() = TextStyle(
    fontFamily = merriweatherFontFamily,
    fontWeight = FontWeight.Black,
    fontSize = 21.sp
)

@Composable
fun genreTextStyle() = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 11.sp
)

@Composable
fun timeTextStyle() = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

@Composable
fun mediumBoldTextStyle() = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp
)

@Composable
fun mediumNormalTextStyle() = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

@Composable
fun mediumSemiBoldTextStyle() = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp
)