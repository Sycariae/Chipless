package com.sycarias.chipless.ui.theme

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.core.view.WindowCompat

@Composable
fun ChiplessShadowStyle(
    style: TextStyle,
    color: Color = Color.Black,
    alpha: Float = 0.6f,
    offsetX: Float = -8f,
    offsetY: Float = 8f,
    blurRadius: Float = 20f
): TextStyle {
    return style.copy(
        shadow = Shadow(
            color = color.copy(alpha = alpha),
            offset = Offset(offsetX, offsetY),
            blurRadius = blurRadius
        )
    )
}

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
fun ChiplessIconButtonColors(
    bg: Color = ChiplessColors.secondary,
    fg: Color = ChiplessColors.textSecondary
): IconButtonColors {
    return IconButtonDefaults.iconButtonColors(
        containerColor = bg,
        contentColor = fg
    )
}

@Composable
fun ChiplessTheme(
    bgColor: Color = ChiplessColors.bgPrimary,
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
            window.navigationBarColor = colors.bgPrimary.toArgb()
        }
    }

    // Apply the theme throughout the app
    ChiplessSurface(bgColor = bgColor, content = content)
}

@Composable
fun ChiplessSurface(
    bgColor: Color = ChiplessColors.bgPrimary,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.Box(
        modifier = androidx.compose.ui.Modifier
            .background(bgColor)
            .fillMaxSize()
    ) {
        content()
    }
}