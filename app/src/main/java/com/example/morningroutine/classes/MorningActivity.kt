package com.example.morningroutine.classes

import androidx.compose.ui.graphics.Color

class MorningActivity(
    val name: String,
    val img: Int,
    val color: Color,
    val contentColor: Color = Color.Black
) {
    var done = false

    companion object {
        val disabledColor: Color = Color.DarkGray
    }
}