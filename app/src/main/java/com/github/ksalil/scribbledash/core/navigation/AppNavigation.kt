package com.github.ksalil.scribbledash.core.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.ksalil.scribbledash.core.navigation.destinations.DifficultyLevelDestination
import com.github.ksalil.scribbledash.core.navigation.destinations.HomeDestination
import com.github.ksalil.scribbledash.drawing.presentation.ChooseDifficultyScreen
import com.github.ksalil.scribbledash.home.presentation.HomeScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination,
        modifier = modifier,
    ) {
        composable<HomeDestination> {
            HomeScreen(
                onOneRoundWonderClick = {
                    Log.d("AppNavigation", "One Round Wonder clicked")
                    navController.navigate(DifficultyLevelDestination)
                }
            )
        }
        composable<DifficultyLevelDestination> {
            ChooseDifficultyScreen(
                onCloseClicked = {
                    navController.popBackStack()
                },
                onDifficultyLevelSelected = { level->
                    Log.d("AppNavigation", "Difficulty Level selected $level")
                }
            )
        }
    }
}