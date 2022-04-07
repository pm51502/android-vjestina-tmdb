package com.example.tmdb.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.tmdb.R

val ProximaNova = FontFamily(
        Font(R.font.proxima_nova_regular),
        Font(R.font.proxima_nova_bold, FontWeight.Bold),
        Font(R.font.proxima_nova_extra_bold, FontWeight.ExtraBold),
)

// Set of Material typography styles to start with
val Typography = Typography(
        body1 = TextStyle(
                fontFamily = ProximaNova,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        ),
        button = TextStyle(
            fontFamily = ProximaNova,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ),
        caption = TextStyle(
            fontFamily = ProximaNova,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        h2 = TextStyle(
            fontFamily = ProximaNova,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            color = Color.White
        ),
        h3 = TextStyle(
            fontFamily = ProximaNova,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            color = Color(0xFF0B253F)
        ),
        h4 = TextStyle(
            fontFamily = ProximaNova,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 22.4.sp,
            //color = Color(0xFF000000)
        ),
        subtitle1 = TextStyle(
            fontFamily = ProximaNova,
            fontSize = 18.sp,
            color = Color.White
        ),
        subtitle2 = TextStyle(
            fontFamily = ProximaNova,
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        ),
)