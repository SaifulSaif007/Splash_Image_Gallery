package com.saiful.splashgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.saiful.presentation.HomeNavRoute
import com.saiful.presentation.homeNavGraph
import com.saiful.presentation.theme.SplashGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            SplashGalleryTheme {
                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = rememberNavController(),
                    startDestination = HomeNavRoute.Root.route
                ) {
                    homeNavGraph()
                }
            }
        }
    }
}
