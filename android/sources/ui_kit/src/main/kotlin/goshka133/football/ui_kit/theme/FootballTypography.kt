package goshka133.football.ui_kit.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Large =
  TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W700,
    fontSize = 24.sp,
    lineHeight = 28.sp,
  )

val BodySemibold =
  TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W500,
    fontSize = 16.sp,
    lineHeight = 20.sp,
  )

val BodyRegular =
  TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    lineHeight = 20.sp,
  )

val CaptionMedium =
  TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp,
    lineHeight = 16.sp,
  )

val CaptionRegular =
  TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W400,
    fontSize = 14.sp,
    lineHeight = 16.sp,
  )

val FootballTypography =
  Typography(
    body1 =
      TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    button = BodySemibold,
    caption =
      TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 12.sp),
  )
