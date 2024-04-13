package com.example.morningroutine.utils

import androidx.compose.ui.graphics.Color

/**
 * return black or white color based on what color is more contrasting with the inputColor
 */
fun getContrastColor(inputColor: Color): Color {
    val luminance = (0.299 * inputColor.red + 0.587 * inputColor.green + 0.114 * inputColor.blue)
    return if (luminance > 0.5) Color.Black else Color.LightGray
}