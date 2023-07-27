package com.example.morningroutine.ui.layout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.R
import com.example.morningroutine.classes.MorningActivity
import com.example.morningroutine.classes.Routine
import com.example.morningroutine.ui.components.MorningActivityView
import com.example.morningroutine.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RoutineLayout(routine: Routine) {
    val basicContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    val basicContainerColor = MaterialTheme.colorScheme.primaryContainer

    val contentColor = remember {
        mutableStateOf(basicContentColor)
    }
    val containerColor = remember {
        mutableStateOf(basicContainerColor)
    }

    fun findNextActivityIndex(): Int {
        return routine.activities.indexOfFirst { !it.done }
    }

    fun updateTitleColor(nextActivityIndex: Int) {
        val nextActivity: MorningActivity? = routine.activities.getOrNull(nextActivityIndex)

        if (nextActivity != null) {
            contentColor.value = nextActivity.contentColor
            containerColor.value = nextActivity.color
        } else {
            contentColor.value = basicContentColor
            containerColor.value = basicContainerColor
        }
    }
    updateTitleColor(findNextActivityIndex())

    val lazyListState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    val centerItemIndex: MutableState<Int> = remember {
        mutableStateOf(0)
    }

    val nextActivity = remember {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Morning Routine")
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
        ) {
            val itemSize = 300.dp
            val itemPadding = 64.dp

            item {
                Spacer(modifier = Modifier.height((itemSize + itemPadding) / 2))
            }

            itemsIndexed(routine.activities) { index, activity ->
                val doneState = remember {
                    mutableStateOf(activity.done)
                }

                val itemScale: Float by animateFloatAsState(
                    targetValue =
                    if (centerItemIndex.value == index) {
                        1.2f
                    } else {
                        1f
                    } - if (doneState.value) {
                        0.3f
                    } else {
                        0f
                    },
                    label = "scale centered item"
                )

                MorningActivityView(
                    modifier = Modifier
                        .size(300.dp)
                        .padding(64.dp)
                        .scale(itemScale),
                    activity = activity,
                ) {
                    activity.done = !activity.done
                    doneState.value = activity.done

                    val nextActivityIndex = findNextActivityIndex()
                    updateTitleColor(nextActivityIndex)
                    nextActivity.value = nextActivityIndex
                }
            }

            item {
                Spacer(modifier = Modifier.height((itemSize + itemPadding) / 2))
            }
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
            .collect { itemsInfo ->
                // minus 1 because of the spacer at the beginning
                centerItemIndex.value = (itemsInfo.first().index + itemsInfo.last().index) / 2 - 1
            }
    }

//    LaunchedEffect(nextActivity) {
//        snapshotFlow { nextActivity.value }
//            .collect { nextActivityIndex ->
//                if (nextActivityIndex >= 0 && nextActivityIndex < routine.activities.size) {
//                    TODO
//                }
//            }
//    }
}


@Preview
@Composable
fun RoutineLayoutPrev() {
    AppTheme {
        val routine = Routine()
            .add(
                MorningActivity(
                    name = "Coffee0",
                    img = R.drawable.coffee,
                    color = Color(228, 162, 80, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Coffee1",
                    img = R.drawable.coffee,
                    color = Color(228, 162, 80, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Coffee2",
                    img = R.drawable.coffee,
                    color = Color(228, 162, 80, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Coffee3",
                    img = R.drawable.coffee,
                    color = Color(228, 162, 80, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Coffee4",
                    img = R.drawable.coffee,
                    color = Color(228, 162, 80, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Meditation",
                    img = R.drawable.meditation,
                    color = Color(129, 252, 129, 255),
                )
            )

        RoutineLayout(routine = routine)
    }
}