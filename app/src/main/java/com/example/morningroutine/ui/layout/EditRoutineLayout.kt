package com.example.morningroutine.ui.layout
//
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.example.morningroutine.R
//import com.example.morningroutine.model.MorningActivity
//import com.example.morningroutine.model.Routine
//import com.example.morningroutine.ui.components.MorningActivityEdit
//import com.example.morningroutine.ui.theme.AppTheme
//
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
//@Composable
//fun EditRoutineLayout(navController: NavController) {
//    val context = LocalContext.current
//    val routine = Routine.loadRoutine(context)
//
//    val routineActivities = remember {
//        mutableStateListOf(*routine.activities.toTypedArray())
//    }
//
//    val lazyListState = rememberLazyListState()
//    val flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
//
//    val itemSize = 64.dp
//    val itemPadding = 16.dp
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(text = "Morning Routine")
//                },
//                actions = {
//                    IconButton(
//                        onClick = {
//                            Routine.saveRoutine(
//                                routine = Routine(ArrayList<MorningActivity>(routineActivities)),
//                                context = context
//                            )
//                            navController.popBackStack()
//                        },
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.outline_save_24),
//                            contentDescription = "Save the morning routine",
//                            tint = MaterialTheme.colorScheme.onPrimary,
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.smallTopAppBarColors(
//                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
//                    containerColor = MaterialTheme.colorScheme.primary,
//                ),
//            )
//        },
//    ) { scaffoldPadding ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(scaffoldPadding)
//                .background(MaterialTheme.colorScheme.background),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            state = lazyListState,
//            flingBehavior = flingBehavior,
//        ) {
//            itemsIndexed(
//                items = routineActivities,
//                key = { index, _ -> index },
//            ) { index, activity ->
//                MorningActivityEdit(
//                    modifier = Modifier
//                        .padding(itemPadding)
//                        .height(itemSize),
//                    activity = activity,
//                    moveDownInList = {
//                        if (index > 0) {
//                            val temp = routineActivities[index - 1]
//                            routineActivities[index - 1] = routineActivities[index]
//                            routineActivities[index] = temp
//                        }
//                    },
//                    moveUpInList = {
//                        if (index < routineActivities.lastIndex) {
//                            val temp = routineActivities[index + 1]
//                            routineActivities[index + 1] = routineActivities[index]
//                            routineActivities[index] = temp
//                        }
//                    },
//                )
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun EditRoutineLayoutPrev() {
//    AppTheme {
//        EditRoutineLayout(navController = NavController(LocalContext.current))
//    }
//}