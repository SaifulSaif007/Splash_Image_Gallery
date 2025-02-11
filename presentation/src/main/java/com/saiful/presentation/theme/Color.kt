package com.saiful.presentation.theme

import androidx.compose.ui.graphics.Color

/**
 * Defines a color shade system based on numerical values.
 *
 * This system categorizes color shades into three groups based on their numerical representation:
 *
 * - **Lighter Shades (50, 100, 200):** Represented by lower numbers, these shades are lighter variations of the base color.
 * - **Medium/Primary Shades (400, 500):** Represented by middle numbers, these shades are considered the primary or medium tones of the color.
 * - **Darker Shades (800, 900):** Represented by higher numbers, these shades are darker variations of the base color.
 *
 * This documentation helps to understand the relationship between the numerical value and the perceived lightness/darkness of a color.
 */

internal object AppColor {

    val Black = Color(0xFF000000)
    val White = Color(0xFFFFFFFF)

    val Gray200 = Color(0xFFCCC9C9)
    val Gray600 = Color(0xD7525252)
}
