package com.github.ksalil.scribbledash.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.ksalil.scribbledash.core.navigation.destinations.DifficultyLevelDestination
import com.github.ksalil.scribbledash.core.navigation.destinations.DrawScreenDestination
import com.github.ksalil.scribbledash.core.navigation.destinations.HomeDestination
import com.github.ksalil.scribbledash.game.presentation.ChooseDifficultyScreen
import com.github.ksalil.scribbledash.game.presentation.DrawScreen
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
                    navController.navigate(DrawScreenDestination(level))
                }
            )
        }
        composable<DrawScreenDestination> { navBackStackEntry ->
            val level = navBackStackEntry.toRoute<DrawScreenDestination>().difficultyLevel
            DrawScreen(
                difficultyLevel = level ,
                onCloseClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}