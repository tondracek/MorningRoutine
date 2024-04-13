package com.example.morningroutine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.morningroutine.domain.crud.FakeCRUD
import com.example.morningroutine.ui.Screen
import com.example.morningroutine.ui.createViewModelFactory
import com.example.morningroutine.ui.routinesessionscreen.RoutineSessionScreen
import com.example.morningroutine.ui.routinesessionscreen.RoutineSessionViewModel
import com.example.morningroutine.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val morningRoutineCRUD = FakeCRUD()

        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.RoutineSession.route
                ) {

                    composable(
                        route = Screen.RoutineSession.route
                    ) {
                        val viewModel by viewModels<RoutineSessionViewModel> {
                            RoutineSessionViewModel(
                                morningRoutineCRUD = morningRoutineCRUD,
                                navController = navController
                            ).createViewModelFactory()
                        }

                        val state by viewModel.state.collectAsState()

                        RoutineSessionScreen(
                            state = state,
                            onEvent = viewModel::onEvent
                        )
                    }
                }
            }
        }
    }
}
