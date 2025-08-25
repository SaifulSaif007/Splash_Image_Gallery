package com.saiful.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.saiful.animated_bottom_bar.ui.AnimatedBottomBar
import com.saiful.animated_bottom_bar.ui.model.BottomBarProperties
import com.saiful.animated_bottom_bar.ui.model.BottomNavItem

@Composable
fun BottomNav(navController: NavHostController) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedBottomBar(
                bottomNavItem = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = Routes.Home,
                        icon = R.drawable.ic_home
                    ),
                    BottomNavItem(
                        name = "Search",
                        route = Routes.Search,
                        icon = R.drawable.ic_search
                    ),
                    BottomNavItem(
                        name = "Profile",
                        route = Routes.OwnProfile,
                        icon = R.drawable.ic_profile
                    )
                ),
                bottomBarProperties = BottomBarProperties(
                    background = MaterialTheme.colorScheme.onPrimary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.primary,
                    labelTextStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                    ),
                    itemArrangement = Arrangement.SpaceBetween
                ),
                onSelectItem = { item, _ ->
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Routes.Home,
            modifier = Modifier.padding(paddingValues)
        ) {
            homeNavGraph(
                navController = navController,
                onError = { exception ->
                    // handleException(exception)
                }
            )
        }
    }
}