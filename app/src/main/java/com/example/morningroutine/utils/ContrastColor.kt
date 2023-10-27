package com.example.morningroutine.utils

import androidx.compose.ui.graphics.Color

/***
 * return black or white color based on what color is more contrasting with the inputColor
 */
fun getContrastColor(inputColor: Color): Color {
    val red = inputColor.red
    val green = inputColor.green
    val blue = inputColor.blue
    val yiq = ((red * 299) + (green * 587) + (blue * 114)) / 1000
    return if (yiq >= 128) {
        Color.Black
    } else {
        Color.White
    }
}