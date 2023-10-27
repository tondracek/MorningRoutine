package com.example.morningroutine.ui.views;

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.morningroutine.model.Routine

/**
 * This is the view that will display the morning activities
 * in the routine as a vertical scrollable list of MorningActivityViews.
 * The scrollable list will be centered on the current activity.
 * The current activity will be scaled up.
 * The previous and next activities will be scaled down.
 * The current activity will be displayed in the center of the screen.
 * The previous and next activities will be displayed above and below the current activity.
 *
 * The scrollable list will be implemented as a LazyColumn.
 */
@Composable
fun RoutineView(
    modifier: Modifier = Modifier,
    routine: Routine,
) {

}