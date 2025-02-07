package com.sycarias.chipless.ui.theme

import androidx.compose.ui.graphics.Color

// Define your custom colours here
val ChiplessColors = ChiplessColorScheme(
    primary = Color(0xFFFF4F77),        // Hot Pink - Focus Elements and Glows
    secondary = Color(0xFF37373C),      // Grey - Main Elements

    accentPrimary = Color(0xFFFF7292),  // Hot Pink - Errors
    accentSecondary = Color(0xFF55555A),// Grey - Element Decorators

    textPrimary = Color(0xFFFFFFFF),    // White - Main Text
    textSecondary = Color(0xFFE2E2E2),  // Light Grey - Secondary Text
    textTertiary = Color(0xFF969699),   // Light-ish Grey - Greyed Out Text

    bgPrimary = Color(0xFF222225),      // Dark Grey - Main Background Color
    bgSecondary = Color(0xFF2C2C30),    // Lighter Dark Grey

    success = Color(0xFF35FF92),        // Jade Green - Confirmation
    warning = Color(0xFFFF9255),        // Orange - Warnings
    greyOut = Color(0xFF2A2A2E)         // Dark-ish Grey - Greyed Out Elements
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
    val greyOut: Color
)
