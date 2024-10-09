package com.sycarias.chipless.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.sycarias.chipless.R

// Define a GoogleFont provider
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Add Poppins from Google Fonts
val Poppins = GoogleFont("Poppins")

// Create the FontFamily for Poppins using different weights
val PoppinsFamily = FontFamily(
    Font(googleFont = Poppins, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = Poppins, fontProvider = provider, weight = FontWeight.Bold)
)

// Set of Material typography styles to start with
val ChiplessTypography = ChiplessTypographyScheme(
    title = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 68.sp,
        lineHeight = 76.sp,
        letterSpacing = (-1).sp
    ),
    h1 = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp,
        lineHeight = 54.sp,
        letterSpacing = (-1).sp
    ),
    h2 = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 42.sp,
        letterSpacing = (-0.25).sp
    ),
    sh1 = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp
    ),
    sh2 = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 1.sp
    ),
    body = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 1.sp
    ),
    l1 = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 1.sp
    ),
    l2 = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 1.sp
    ),
    l3 = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        lineHeight = 18.sp,
        letterSpacing = 1.2.sp
    ),
)

data class ChiplessTypographyScheme(
    val title: TextStyle,   // 56px - Title
    val h1: TextStyle,      // 36px - Heading 1
    val h2: TextStyle,      // 30px - Heading 2
    val sh1: TextStyle,     // 24px - Subheading 1
    val sh2: TextStyle,     // 18px - Subheading 2
    val body: TextStyle,    // 16px - Body
    val l1: TextStyle,      // 14px - Label 1 ( Smol )
    val l2: TextStyle,      // 12px - Label 2 ( Smol'er )
    val l3: TextStyle,      // 10px - Label 3 ( Smol'est )
)