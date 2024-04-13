package com.example.morningroutine.ui.routinesessionscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.R
import com.example.morningroutine.domain.model.MorningActivity
import com.example.morningroutine.ui.Screen
import com.example.morningroutine.ui.components.IconResource
import com.example.morningroutine.ui.routinesessionscreen.activitysessionview.MorningActivitySessionView
import com.example.morningroutine.ui.theme.AppTheme
import com.example.morningroutine.utils.getContrastColor
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineSessionScreen(
    state: RoutineSessionState,
    onEvent: (RoutineSessionEvent) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_title))
                },
                actions = {
                    IconButton(onClick = {
                        onEvent(RoutineSessionEvent.Navigate(Screen.EditRoutine))
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_edit_24),
                            contentDescription = "Edit the morning routine",
                        )
                    }
                },
                colors = customTopBarColors(
                    customColor = (state as? RoutineSessionState.Success)?.customTopBarColor
                )
            )
        },
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is RoutineSessionState.Loading -> RoutineSessionScreenLoading()

                is RoutineSessionState.Success -> RoutineSessionScreenSuccess(
                    state = state,
                    onEvent = onEvent,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun customTopBarColors(customColor: Color? = null): TopAppBarColors {
    return if (customColor != null) {
        TopAppBarDefaults.topAppBarColors(
            containerColor = customColor,
            titleContentColor = getContrastColor(customColor)
        )
    } else {
        TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

@Composable
private fun RoutineSessionScreenLoading() {
    Text(
        text = stringResource(R.string.loading),
        style = MaterialTheme.typography.titleLarge
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RoutineSessionScreenSuccess(
    state: RoutineSessionState.Success,
    onEvent: (RoutineSessionEvent) -> Unit,
) {
    val localDensity = LocalDensity.current

    val lazyListState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    val cardSize = 200.dp

    val currentActivityIndex by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.minByOrNull {
                abs(it.offset)
            }?.index ?: 0
        }
    }

    val contentPadding by remember {
        derivedStateOf {
            with(localDensity) {
                (lazyListState.layoutInfo.viewportSize.height.toDp() - cardSize) / 2
            }.takeIf { it > 0.dp } ?: 100.dp
        }
    }

    LaunchedEffect(state.activities.count { it.isDone }) {
        val nextUnfinishedActivity = state.activities.indexOfFirst { !it.isDone }

        if (nextUnfinishedActivity >= 0 && nextUnfinishedActivity > currentActivityIndex) {
            lazyListState.animateScrollToItem(nextUnfinishedActivity)
        }
    }

    // TODO check Layout inspector for number of recompositions (when clicking everything recomposes)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        flingBehavior = flingBehavior,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(contentPadding - cardSize / 2),
        contentPadding = PaddingValues(vertical = contentPadding),
    ) {

        itemsIndexed(state.activities) { index, activity ->
            val targetScale = when {
                index == currentActivityIndex && activity.isDone -> 0.8f
                activity.isDone -> 0.6f
                index == currentActivityIndex -> 1.2f
                else -> 1f
            }
            val scale by animateFloatAsState(
                targetValue = targetScale,
                label = ""
            )

            MorningActivitySessionView(
                modifier = Modifier
                    .size(cardSize)
                    .scale(scale),
                state = activity,
                onClick = {
                    onEvent(RoutineSessionEvent.ToggleActivityDone(activity.id))
                }
            )
        }
    }
}

@Preview
@Composable
private fun RoutineSessionScreenSuccessPrev() {
    RoutineSessionScreen(
        state = RoutineSessionState.Success(
            activities = List(5) {
                MorningActivity(
                    name = "Test",
                    iconSource = IconResource(R.drawable.icon_sun),
                    containerColor = Color.Yellow,
                )
            }.toMutableStateList(),
            customTopBarColor = Color.Cyan,
        ),
        onEvent = {}
    )
}

@Preview
@Composable
private fun RoutineSessionScreenLoadingPrev() {
    AppTheme {
        RoutineSessionScreen(
            state = RoutineSessionState.Loading,
            onEvent = {}
        )
    }
}

