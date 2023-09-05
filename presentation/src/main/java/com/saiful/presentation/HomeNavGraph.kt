package com.saiful.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

private const val HOME = "home"

sealed class HomeNavRoute(val route: String) {
    object Root : HomeNavRoute("$HOME/")
    object Home : HomeNavRoute("${HOME}/home")
}

fun NavGraphBuilder.homeNavGraph() {

    navigation(
        route = HomeNavRoute.Root.route,
        startDestination = HomeNavRoute.Home.route
    ) {
        composable(
            route = HomeNavRoute.Home.route
        ) {
            HomeScreen()
        }
    }
}