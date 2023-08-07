package com.example.morningroutine.ui.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.morningroutine.R
import com.example.morningroutine.ui.components.MorningActivityEdit
import com.example.morningroutine.ui.theme.AppTheme
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun EditRoutineLayout(navController: NavController) {

    val morningActivities = loadMorningRoutine().activities.toMutableList()

    val lazyListState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    val itemSize = 64.dp
    val itemPadding = 16.dp

    val state = rememberReorderableLazyListState(
        onMove = { a, b ->
            morningActivities.swap(a.index, b.index)
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Morning Routine")
                },
                actions = {
                    IconButton(
                        onClick = {
                            // TODO
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_save_24),
                            contentDescription = "Save the morning routine",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .background(MaterialTheme.colorScheme.background)
                .reorderable(
                    state = state,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = lazyListState,
            flingBehavior = flingBehavior,
        ) {
            itemsIndexed(
                items = morningActivities,
                key = { index, _ ->
                    index
                },
            ) { _, activity ->
                MorningActivityEdit(
                    modifier = Modifier
                        .padding(itemPadding)
                        .height(itemSize)
                        .detectReorderAfterLongPress(state = state),
                    activity = activity,
                )
            }
        }
    }
}

@Preview
@Composable
fun EditRoutineLayoutPrev() {
    AppTheme {
        EditRoutineLayout(navController = NavController(LocalContext.current))
    }
}

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    if (index1 < 0 || index1 >= size || index2 < 0 || index2 >= size) {
        throw IndexOutOfBoundsException("Indices out of bounds.")
    }

    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}