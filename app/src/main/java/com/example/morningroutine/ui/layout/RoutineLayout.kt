package com.example.morningroutine.ui.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.R
import com.example.morningroutine.classes.MorningActivity
import com.example.morningroutine.classes.Routine
import com.example.morningroutine.ui.components.MorningActivityView
import com.example.morningroutine.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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

    fun updateTitleColor(nextActivity: MorningActivity?) {
        if (nextActivity != null) {
            contentColor.value = nextActivity.contentColor
            containerColor.value = nextActivity.color
        } else {
            contentColor.value = basicContentColor
            containerColor.value = basicContainerColor
        }
    }
    updateTitleColor(routine.activities.first())

    val lazyListState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

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
            flingBehavior = snapBehavior,
        ) {
            val itemSize = 300.dp
            val itemPadding = 64.dp

            item {
                Spacer(modifier = Modifier.height((itemSize + itemPadding) / 2))
            }
            for (activity in routine.activities) {
                item {
                    MorningActivityView(
                        modifier = Modifier
                            .size(300.dp)
                            .padding(64.dp),
                        activity = activity,
                    ) {
                        activity.done = !activity.done

                        val nextActivity: MorningActivity? = routine.activities.find { !it.done }
                        updateTitleColor(nextActivity)
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height((itemSize + itemPadding) / 2))
            }
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
                    name = "Coffee",
                    img = R.drawable.coffee,
                    color = Color(228, 162, 80, 255),
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
                    name = "Coffee",
                    img = R.drawable.coffee,
                    color = Color(228, 162, 80, 255),
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
                    name = "Coffee",
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