package com.example.morningroutine.ui.layout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import com.example.morningroutine.R
import com.example.morningroutine.classes.MorningActivity
import com.example.morningroutine.classes.Routine
import com.example.morningroutine.ui.components.MorningActivityView
import com.example.morningroutine.ui.theme.AppTheme
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RoutineLayout(routine: Routine) {
    val density = LocalDensity.current

    val basicContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    val basicContainerColor = MaterialTheme.colorScheme.primaryContainer

    val contentColor = remember {
        mutableStateOf(basicContentColor)
    }
    val containerColor = remember {
        mutableStateOf(basicContainerColor)
    }

    val lazyListState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    val centerItemIndex: MutableState<Int> = remember {
        mutableStateOf(0)
    }

    val itemSize = 250.dp
    val itemPadding = 64.dp

    val paddingAroundContent = remember {
        derivedStateOf {
            val centerDp = with(density) {
                lazyListState.layoutInfo.viewportSize.center.y.toDp()
            }

            (centerDp - itemPadding - itemSize / 2)
                .coerceAtLeast(0.dp)
        }
    }

    fun countDoneActivities(): Int {
        return routine.activities.count { it.done }
    }

    var doneActivitiesCount by remember {
        mutableStateOf(countDoneActivities())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Morning Routine")
                        Text(text = "$doneActivitiesCount / ${routine.activities.size}")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    titleContentColor = contentColor.value,
                    containerColor = containerColor.value,
                ),
            )
        },
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = lazyListState,
            flingBehavior = flingBehavior,
            contentPadding = PaddingValues(
                top = paddingAroundContent.value,
                bottom = paddingAroundContent.value,
            )
        ) {
            itemsIndexed(routine.activities) { index, activity ->
                val doneState = remember {
                    mutableStateOf(activity.done)
                }

                val itemScale: Float by animateFloatAsState(
                    targetValue =
                    if (centerItemIndex.value == index) {
                        1f
                    } else {
                        0.8f
                    } - if (doneState.value) {
                        0.3f
                    } else {
                        0f
                    },
                    label = "scale centered item"
                )

                MorningActivityView(
                    modifier = Modifier
                        .padding(itemPadding)
                        .size(itemSize)
                        .scale(itemScale),
                    activity = activity,
                ) {
                    activity.done = !activity.done
                    doneState.value = activity.done
                    doneActivitiesCount = countDoneActivities()
                }
            }
        }
    }

    fun setMostCenteredItem(itemsInfo: List<LazyListItemInfo>) {
        val center = lazyListState.layoutInfo.viewportSize.center.y
        val mostCenteredItem = itemsInfo.minByOrNull {
            val pos =
                lazyListState.layoutInfo.beforeContentPadding + it.offset + it.size / 2
            abs(center - pos)
        }

        if (mostCenteredItem != null) {
            centerItemIndex.value = mostCenteredItem.index
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
            .collect { itemsInfo ->
                setMostCenteredItem(itemsInfo)
            }
    }

    fun updateTitleColor() {
        val nextActivity = routine.activities.find { !it.done }

        if (nextActivity != null) {
            contentColor.value = nextActivity.contentColor
            containerColor.value = nextActivity.color
        } else {
            contentColor.value = basicContentColor
            containerColor.value = basicContainerColor
        }
    }
    updateTitleColor()

    suspend fun scrollToNext() {
        val actual = centerItemIndex.value

        val nextUndoneActivityIndex = routine.activities
            .withIndex()
            .indexOfFirst { (index, activity) -> !activity.done && index >= actual }

        if (nextUndoneActivityIndex >= 0 && nextUndoneActivityIndex <= routine.activities.lastIndex) {
            lazyListState.animateScrollToItem(nextUndoneActivityIndex)
        }
    }

    LaunchedEffect(doneActivitiesCount) {
        snapshotFlow { doneActivitiesCount }
            .collect {
                updateTitleColor()
                scrollToNext()
            }
    }
}


@Preview
@Composable
fun RoutineLayoutPrev() {
    AppTheme {
        val routine = Routine()
            .add(
                MorningActivity(
                    name = "Wash your face",
                    img = R.drawable.sink,
                    color = Color(0, 188, 212, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Beard oil",
                    img = R.drawable.skincare,
                    color = Color(255, 235, 59, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Vitamins + creatine",
                    img = R.drawable.suplements,
                    color = Color(139, 195, 74, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Coffee",
                    img = R.drawable.coffee,
                    color = Color(228, 162, 80, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Bathroom",
                    img = R.drawable.toilet,
                    color = Color(33, 150, 243, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Breakfast",
                    img = R.drawable.breakfast,
                    color = Color(255, 87, 34, 255),
                )
            )

        RoutineLayout(routine = routine)
    }
}