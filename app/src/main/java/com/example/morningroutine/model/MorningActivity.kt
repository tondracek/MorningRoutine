package com.example.morningroutine.model

import androidx.compose.ui.graphics.Color
import com.example.morningroutine.utils.getContrastColor

/**
 * This a morning activity model class.
 */
class MorningActivity(
    var name: String,
    var iconSource: String,
    containerColor: Color,
    var done: Boolean = false,
) {
    var containerColor = containerColor
        set(value) {
            field = value
            contentColor = getContrastColor(value)
        }
    var contentColor = getContrastColor(containerColor)
        private set
}