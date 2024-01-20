package com.saiful.splashgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.saiful.core.components.logger.logError
import com.saiful.core.components.logger.logInfo
import com.saiful.core.domain.DomainException
import com.saiful.presentation.HomeNavRoute
import com.saiful.presentation.homeNavGraph
import com.saiful.presentation.theme.SplashGalleryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            SplashGalleryTheme {
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()

                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = HomeNavRoute.Root.route
                ) {
                    homeNavGraph(
                        navController = navController,
                        coroutineScope = coroutineScope,
                        onError = { exception ->
                            handleException(exception)
                        }
                    )
                }

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    logInfo(msg = "destination -> $destination")
                    handleStatusBar(destination)
                }
            }
        }
    }

    private fun handleException(exception: DomainException) {
        //todo -> add toast or snack bar
        logError(msg = exception.message)
    }

    private fun handleStatusBar(destination: NavDestination) {
        val currentDestination = destination.route
        when (currentDestination) {
            HomeNavRoute.PhotoDetails.route -> {
                window.statusBarColor = Color.Transparent.copy(alpha = 0.3f).toArgb()
                WindowCompat.setDecorFitsSystemWindows(window, false)
                WindowCompat.getInsetsController(window,  window.decorView).isAppearanceLightStatusBars = false
            }

            else -> {
                window.statusBarColor = Color.Transparent.toArgb()
                WindowCompat.setDecorFitsSystemWindows(window, true)
                WindowCompat.getInsetsController(window,  window.decorView).isAppearanceLightStatusBars = true
            }
        }
    }
}
