package com.saiful.splashgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.saiful.core.components.logger.logError
import com.saiful.core.components.logger.logInfo
import com.saiful.core.domain.DomainException
import com.saiful.presentation.Routes
import com.saiful.presentation.homeNavGraph
import com.saiful.presentation.theme.SplashGalleryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        enableEdgeToEdge()

        setContent {
            SplashGalleryTheme {
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()
                val currentDestination =
                    remember { mutableStateOf(navController.currentBackStackEntry?.destination) }

                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = Routes.Home
                ) {

                    homeNavGraph(
                        navController = navController,
                        onError = { exception ->
                            handleException(exception)
                        }
                    )
                }

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    logInfo(msg = "destination -> $destination")
                    currentDestination.value = destination
                }


                when (currentDestination.value?.hasRoute(Routes.PhotoDetails::class)) {
                    true -> {
                        window.statusBarColor = Color.Transparent.copy(alpha = 0.3f).toArgb()
                        WindowCompat.setDecorFitsSystemWindows(window, false)
                        WindowCompat.getInsetsController(
                            window,
                            window.decorView
                        ).isAppearanceLightStatusBars = false

                        window.navigationBarColor = android.graphics.Color.TRANSPARENT
                    }

                    else -> {
                        val darkTheme = isSystemInDarkTheme()
                        window.statusBarColor = MaterialTheme.colorScheme.surface.toArgb()
                        WindowCompat.setDecorFitsSystemWindows(window, true)
                        WindowCompat.getInsetsController(
                            window,
                            window.decorView
                        ).isAppearanceLightStatusBars = !darkTheme

                        window.navigationBarColor = android.graphics.Color.TRANSPARENT
                    }
                }
            }
        }
    }

    private fun handleException(exception: DomainException) {
        //todo -> add toast or snack bar
        logError(msg = exception.message)
    }

}
