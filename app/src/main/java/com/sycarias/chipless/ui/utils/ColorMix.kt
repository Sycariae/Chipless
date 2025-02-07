package com.sycarias.chipless.ui.utils

import androidx.compose.ui.graphics.Color

fun colorMix(color1: Color, color2: Color, ratio: Float): Color {
    // Ensure the ratio is clamped between 0 and 1
    val clampedRatio = ratio.coerceIn(0f, 1f)
    val inverseRatio = 1f - clampedRatio

    // Calculate the mixed colour components
    val red = (color1.red * clampedRatio) + (color2.red * inverseRatio)
    val green = (color1.green * clampedRatio) + (color2.green * inverseRatio)
    val blue = (color1.blue * clampedRatio) + (color2.blue * inverseRatio)
    val alpha = (color1.alpha * clampedRatio) + (color2.alpha * inverseRatio)

    // Return the resulting mixed colour
    return Color(red, green, blue, alpha)
}