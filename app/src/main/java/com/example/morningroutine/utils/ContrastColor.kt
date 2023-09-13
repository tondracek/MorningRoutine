package com.example.morningroutine.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun getContrastColor(inputColor: Int): Int {
    // Extract color components (red, green, blue) from the inputColor
    val red = (inputColor shr 16) and 0xFF
    val green = (inputColor shr 8) and 0xFF
    val blue = inputColor and 0xFF

    // Calculate relative luminance using the formula for sRGB colors
    val relativeLuminance = 0.2126 * red + 0.7152 * green + 0.0722 * blue

    // Set a threshold value for luminance (0 to 255) to determine contrast
    val luminanceThreshold = 128

    // Choose the appropriate text color based on luminance
    return if (relativeLuminance > luminanceThreshold) {
        Color.Black.toArgb()
    } else {
        Color.White.toArgb()
    }
}