package com.company.myapplication.android.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * A variable named typography that’s an instance of the [Typography].
 * for use in Material Design.
 */
val typography = Typography(
    // Override the predefined h1 type.
    h1 = TextStyle(
        // Define the font family to use. You’ll use the SansSerif family
        fontFamily = FontFamily.SansSerif,
        // Set the font size.
        fontSize = 24.sp,
        // Set the font weight.
        fontWeight = FontWeight.Bold,
        // Set the font color.
        color = Color.White
    ),

    h2 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 20.sp,
        color = Color.White
    ),

    h3 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 12.sp,
        color = Color.White
    ),

    h4 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 10.sp,
        color = Color.White
    )
)
