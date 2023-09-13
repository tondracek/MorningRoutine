package com.example.morningroutine.classes

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.morningroutine.utils.getContrastColor
import java.io.Serializable


class MorningActivity(
    var name: String,
    private var icon: Int,
    private var containerColor: Int,
    private var contentColor: Int = Color.Black.toArgb(),
) : Serializable {
    var done = false

    fun setIcon(icon: Int) {
        this.icon = icon
    }

    fun setContainerColor(newColor: Int) {
        containerColor = newColor
        contentColor = getContrastColor(newColor)
    }

    fun getIcon(): Int = icon
    fun getContainerColor(): Color = Color(containerColor)
    fun getContentColor(): Color = Color(contentColor)

    companion object {
        val disabledColor: Int = Color.DarkGray.toArgb()

        fun getDisabledColor(): Color {
            return Color(disabledColor)
        }
    }
}