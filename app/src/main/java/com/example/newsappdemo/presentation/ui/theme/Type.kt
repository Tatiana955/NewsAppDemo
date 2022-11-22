package com.example.newsappdemo.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.newsappdemo.R

val Typography = Typography(
    headlineSmall = TextStyle(
        fontSize = 24.sp,
        fontFamily = FontFamily(Font(R.font.roboto_bold))
    ),
    titleMedium = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.roboto_bold))
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
    ),
    labelLarge = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(R.font.roboto_light)),
        textAlign = TextAlign.End
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(R.font.roboto_italic)),
        textAlign = TextAlign.End
    )
)