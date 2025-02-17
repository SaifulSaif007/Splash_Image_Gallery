package com.saiful.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = AppColor.Black,
    onPrimary = AppColor.White,
    outlineVariant = AppColor.Gray200, //divider, outlineCard
    tertiary = AppColor.Gray600,
    onTertiary = AppColor.White
)

private val DarkColorScheme = darkColorScheme(
    primary = AppColor.White,
    onPrimary = AppColor.Black,
    outlineVariant = AppColor.Gray200,
    tertiary = AppColor.Gray200,
    onTertiary = AppColor.White
)

@Composable
fun SplashGalleryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (!isSystemInDarkTheme()) {
        LightColorScheme
    } else {
        DarkColorScheme
    }


    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            //window?.statusBarColor = colorScheme.surface.toArgb()

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}