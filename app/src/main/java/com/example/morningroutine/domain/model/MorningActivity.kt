package com.example.morningroutine.domain.model

import androidx.compose.ui.graphics.Color
import com.example.morningroutine.ui.components.IconResource
import com.example.morningroutine.utils.getContrastColor

/**
 * This a morning activity model class.
 */
data class MorningActivity(
    var id: ActivityId = ActivityId.UNINITIALIZED,
    var name: String,
    var iconSource: IconResource,
    var containerColor: Color,
    var isDone: Boolean = false,
) {
    val contentColor: Color
        get() = getContrastColor(containerColor)
}

//typealias ActivityId = Long

data class ActivityId(val value: Long) {
    companion object {
        val UNINITIALIZED = ActivityId(0L)
    }
}