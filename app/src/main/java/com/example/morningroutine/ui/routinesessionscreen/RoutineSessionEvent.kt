package com.example.morningroutine.ui.routinesessionscreen

import com.example.morningroutine.domain.model.ActivityId
import com.example.morningroutine.ui.Screen

sealed interface RoutineSessionEvent {

    data class Navigate(val screen: Screen) : RoutineSessionEvent

    data class ToggleActivityDone(val id: ActivityId) : RoutineSessionEvent

}
