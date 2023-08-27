package com.example.morningroutine.classes

import androidx.compose.ui.graphics.Color
import java.io.Serializable


class MorningActivity(
    var name: String,
    val img: Int,
    val containerColor: Color,
    val contentColor: Color = Color.Black,
) : Serializable {
    var done = false

    companion object {
        val disabledColor: Color = Color.DarkGray
    }
}