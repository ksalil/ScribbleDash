package com.github.ksalil.scribbledash.core.navigation

import androidx.annotation.DrawableRes
import com.github.ksalil.scribbledash.R
import com.github.ksalil.scribbledash.core.navigation.destinations.ChartDestination
import com.github.ksalil.scribbledash.core.navigation.destinations.HomeDestination

data class BottomNavItem(
    val route: Any,
    @DrawableRes val iconResId: Int,
    val contentDescription: String
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = HomeDestination,
        iconResId = R.drawable.ic_chart,
        contentDescription = "Chart"
    ),
    BottomNavItem(
        route = ChartDestination,
        iconResId = R.drawable.ic_home,
        contentDescription = "Home"
    )
)
