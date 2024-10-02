package com.sycarias.chipless.ui.theme

import androidx.compose.ui.graphics.Color

// Define your custom colours here
val ChiplessColors = ChiplessColorScheme(
    primary = Color(0xFFFF4F77),        // Hot Pink
    secondary = Color(0xFF37373C),      // Grey

    accentPrimary = Color(0xFFFF7292),  // Hot Pink
    accentSecondary = Color(0xFF55555A),// Grey

    textPrimary = Color(0xFFFFFFFF),    // White
    textSecondary = Color(0xFFE2E2E2),  // Light Grey
    textTertiary = Color(0xFFADADB2),   // Lighter Grey

    bgPrimary = Color(0xFF222225),      // Dark Grey
    bgSecondary = Color(0xFF2C2C30),    // Lighter Dark Grey

    success = Color(0xFF35FF92),        // Jade Green
    warning = Color(0xFFFF9255),        // Orange
    error = Color(0xFFEB3333)           // Red
)

// Define your own custom color scheme class
data class ChiplessColorScheme(
    val primary: Color,
    val secondary: Color,

    val accentPrimary: Color,
    val accentSecondary: Color,

    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,

    val bgPrimary: Color,
    val bgSecondary: Color,

    val success: Color,
    val warning: Color,
    val error: Color
)
