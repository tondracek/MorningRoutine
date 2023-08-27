package com.example.morningroutine.ui.layout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.morningroutine.R
import com.example.morningroutine.classes.Routine
import com.example.morningroutine.ui.components.MorningActivityView
import com.example.morningroutine.ui.theme.AppTheme
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RoutineLayout(navController: NavController) {
    val density = LocalDensity.current
    val context = LocalContext.current

    val routine = Routine.loadRoutine(context)
    val routineActivities = remember {
        routine.activities
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
        return routineActivities.count { it.done }
    }

    var doneActivitiesCount by remember {
        mutableStateOf(countDoneActivities())
    }

    val basicContentColor = MaterialTheme.colorScheme.onPrimary
    val basicContainerColor = MaterialTheme.colorScheme.primary

    val contentColor = remember(doneActivitiesCount) {
        derivedStateOf {
            routineActivities.find { !it.done }?.contentColor ?: basicContentColor
        }
    }
    val containerColor = remember(doneActivitiesCount) {
        derivedStateOf {
            routineActivities.find { !it.done }?.containerColor ?: basicContainerColor
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Morning Routine")
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("editMorningRoutineLayout")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_edit_24),
                            contentDescription = "Edit the morning routine",
                            tint = contentColor.value,
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    titleContentColor = contentColor.value,
                    containerColor = containerColor.value,
                ),
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .testTag("progressText"),
                        text = "$doneActivitiesCount/${routineActivities.size}",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                    )
                    val progressAnimationValue by animateFloatAsState(
                        targetValue =
                        if (routineActivities.isNotEmpty())
                            doneActivitiesCount.toFloat() / routineActivities.size
                        else
                            0f,
                        label = "progressAnimationValue"
                    )
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        progress = progressAnimationValue,
                        color = MaterialTheme.colorScheme.tertiary,
                        trackColor = MaterialTheme.colorScheme.onTertiary,
                    )
                }
            }
        }
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
            itemsIndexed(
                items = routineActivities,
                key = { index, _ ->
                    index
                },
            ) { index, activity ->
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
                        .scale(itemScale)
                        .testTag("activityCard"),
                    activity = activity,
                ) {
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

    suspend fun scrollToNext() {
        val actual = centerItemIndex.value

        val nextUndoneActivityIndex = routineActivities
            .withIndex()
            .indexOfFirst { (index, activity) -> !activity.done && index >= actual }

        if (nextUndoneActivityIndex >= 0 && nextUndoneActivityIndex <= routineActivities.lastIndex) {
            lazyListState.animateScrollToItem(nextUndoneActivityIndex)
        }
    }

    LaunchedEffect(doneActivitiesCount) {
        snapshotFlow { doneActivitiesCount }
            .collect {
                scrollToNext()
            }
    }
}

@Preview
@Composable
fun RoutineLayoutPrev() {
    AppTheme {
        RoutineLayout(NavController(LocalContext.current))
    }
}