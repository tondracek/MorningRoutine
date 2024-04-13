package com.example.morningroutine.ui.routinesessionscreen

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.example.morningroutine.domain.model.MorningActivity

sealed interface RoutineSessionState {

    data object Loading : RoutineSessionState

    data class Success(
        val activities: SnapshotStateList<MorningActivity>,
        val customTopBarColor: Color? = null,
    ) : RoutineSessionState
}