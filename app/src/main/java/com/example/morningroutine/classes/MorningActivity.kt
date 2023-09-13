package com.example.morningroutine.classes

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.morningroutine.utils.getContrastColor
import java.io.Serializable


class MorningActivity(
    var name: String,
    val img: Int,
    private var containerColor: Int,
    private var contentColor: Int = Color.Black.toArgb(),
) : Serializable {
    var done = false

    fun getContainerColor(): Color = Color(containerColor)
    fun setContainerColor(newColor: Int) {
        containerColor = newColor
        contentColor = getContrastColor(newColor)
    }

    fun getContentColor(): Color = Color(contentColor)

    companion object {
        val disabledColor: Int = Color.DarkGray.toArgb()

        fun getDisabledColor(): Color {
            return Color(disabledColor)
        }
    }
}