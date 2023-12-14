package com.saiful.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.saiful.core.domain.DomainException
import com.saiful.presentation.photodetails.PhotoDetailsScreen
import com.saiful.presentation.utils.Constants.PHOTO_ID
import kotlinx.coroutines.CoroutineScope

private const val HOME = "home"

sealed class HomeNavRoute(val route: String) {
    object Root : HomeNavRoute("$HOME/")
    object Home : HomeNavRoute("${HOME}/home")
    object PhotoDetails : HomeNavRoute("${HOME}/photodetails?$PHOTO_ID={$PHOTO_ID}") {
        fun createRoute(photoId: String) = "$HOME/photodetails?$PHOTO_ID=${photoId}"
    }
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
            HomeScreen(onError = onError) { photoId ->
                navController.navigate(HomeNavRoute.PhotoDetails.createRoute(photoId))
            }
        }

        composable(
            route = HomeNavRoute.PhotoDetails.route,
            arguments = listOf(navArgument(PHOTO_ID) { type = NavType.StringType })
        ) {
            PhotoDetailsScreen()
        }
    }
}