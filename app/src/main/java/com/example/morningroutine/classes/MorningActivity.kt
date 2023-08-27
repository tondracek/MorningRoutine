package com.example.morningroutine.classes

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import java.io.Serializable


class MorningActivity(
    var name: String,
    val img: Int,
    private val containerColor: Int,
    private val contentColor: Int = Color.Black.toArgb(),
) : Serializable {
    var done = false

    fun getContainerColor(): Color = Color(containerColor)

    fun getContentColor(): Color = Color(contentColor)

    companion object {
        val disabledColor: Int = Color.DarkGray.toArgb()

        fun getDisabledColor(): Color {
            return Color(disabledColor)
        }
    }
}