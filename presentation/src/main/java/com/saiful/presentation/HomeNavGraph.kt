package com.saiful.presentation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.saiful.core.domain.DomainException
import kotlinx.coroutines.CoroutineScope

private const val HOME = "home"

sealed class HomeNavRoute(val route: String) {
    object Root : HomeNavRoute("$HOME/")
    object Home : HomeNavRoute("${HOME}/home")
}

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    coroutineScope: CoroutineScope,
    onError: (DomainException) -> Unit
) {

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