package com.sycarias.chipless.ui.theme

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun ChiplessButtonColors(
    bg: Color = ChiplessColors.secondary,
    fg: Color = ChiplessColors.textSecondary
): ButtonColors {
    return ButtonDefaults.buttonColors(
        containerColor = bg,
        contentColor = fg
    )
}

@Composable
fun ChiplessTheme(
    content: @Composable () -> Unit
) {
    // Choose between light and dark themes
    val colors = ChiplessColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.bgPrimary.toArgb() // Set status bar to match the theme
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    // Apply the theme throughout the app
    ChiplessSurface(colors = colors, content = content)
}

@Composable
fun ChiplessSurface(
    colors: ChiplessColorScheme,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.Box(
        modifier = androidx.compose.ui.Modifier
            .background(colors.bgPrimary)
            .fillMaxSize()
    ) {
        content()
    }
}