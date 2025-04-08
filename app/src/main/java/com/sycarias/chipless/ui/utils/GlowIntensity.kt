package com.sycarias.chipless.ui.utils

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class GlowIntensity(
    val inner: GlowSettings,
    val outer: GlowSettings
) {
    HIGH(
        inner = GlowSettings(glow = 0.4f, blur = 22.dp),
        /*secondary = GlowSettings(glow = 1f, blur = 8.dp)*/
        outer = GlowSettings(glow = 0.2f, blur = 35.dp),
        /*secondary = GlowSettings(glow = 0.5f, blur = 6.dp)*/
    ),
    LOW(
        inner = GlowSettings(glow = 0.25f, blur = 18.dp),
        /*secondary = GlowSettings(glow = 0.4f, blur = 8.dp)*/
        outer = GlowSettings(glow = 0.1f, blur = 25.dp),
        /*secondary = GlowSettings(glow = 0.3f, blur = 6.dp)*/
    );
}

@Immutable
data class GlowSettings(val glow: Float, val blur: Dp)
