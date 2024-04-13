package com.example.morningroutine.ui.routinesessionscreen

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.morningroutine.domain.crud.MorningRoutineCRUD
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RoutineSessionViewModel(
    private val morningRoutineCRUD: MorningRoutineCRUD,
    private val navController: NavController,
) : ViewModel() {

    val state: StateFlow<RoutineSessionState> = morningRoutineCRUD.getMorningRoutine()
        .map {
            RoutineSessionState.Success(
                activities = it.activities.toMutableStateList(),
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = RoutineSessionState.Loading
        )

    fun onEvent(event: RoutineSessionEvent) {
        when (event) {
            is RoutineSessionEvent.Navigate -> {
                navController.navigate(event.screen.route)
            }

            is RoutineSessionEvent.ToggleActivityDone -> viewModelScope.launch {
                morningRoutineCRUD.toggleActivityDone(event.id)
            }
        }
    }
}