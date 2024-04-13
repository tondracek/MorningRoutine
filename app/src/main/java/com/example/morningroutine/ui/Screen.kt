package com.example.morningroutine.ui

sealed class Screen(val route: String) {

    data object RoutineSession : Screen("routineSession")

    data object EditRoutine : Screen("editRoutine")
}
