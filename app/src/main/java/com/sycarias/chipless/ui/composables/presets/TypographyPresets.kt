package com.sycarias.chipless.ui.composables.presets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.ui.theme.ChiplessShadowStyle
import com.sycarias.chipless.ui.theme.ChiplessTypography

@Composable
fun Title(
    text: String,
    color: Color = ChiplessColors.textPrimary,
    textAlign: TextAlign? = TextAlign.Center
) {
    Text(
        text = text,
        style = ChiplessShadowStyle(
            style = ChiplessTypography.title,
            offsetX = -12f,
            offsetY = 12f,
            blurRadius = 25f
        ),
        color = color,
        textAlign = textAlign
    )
}

@Composable
fun Heading(
    text: String,
    color: Color = ChiplessColors.textPrimary,
    textAlign: TextAlign? = TextAlign.Center
) {
     Text(
         text = text,
         style = ChiplessShadowStyle(
             style = ChiplessTypography.h1,
             offsetX = -12f,
             offsetY = 12f,
             blurRadius = 25f
         ),
         color = color,
         textAlign = textAlign
     )
}

@Composable
fun Subtitle(
    text: String,
    color: Color = ChiplessColors.textSecondary,
    textAlign: TextAlign? = TextAlign.Center
) {
    Text(
        text = text,
        style = ChiplessShadowStyle(
            style = ChiplessTypography.h2,
            offsetX = -12f,
            offsetY = 12f,
            blurRadius = 25f
        ),
        color = color,
        textAlign = textAlign
    )
}

@Composable
fun Subheading(
    text: String,
    color: Color = ChiplessColors.textTertiary,
    textAlign: TextAlign? = TextAlign.Center
) {
    Text(
        text = text,
        style = ChiplessShadowStyle(
            style = ChiplessTypography.sh2,
            offsetX = -8f,
            offsetY = 8f,
            blurRadius = 20f
        ),
        color = color,
        textAlign = textAlign
    )
}

@Composable
fun Body(
    text: String,
    color: Color = ChiplessColors.textSecondary,
    textAlign: TextAlign? = null,
    softWrap: Boolean = true
) {
    Text(
        text = text,
        style = ChiplessShadowStyle(
            style = ChiplessTypography.body,
            offsetX = -8f,
            offsetY = 8f,
            blurRadius = 15f
        ),
        color = color,
        textAlign = textAlign,
        softWrap = softWrap
    )
}

@Composable
fun Label(
    text: String,
    color: Color = ChiplessColors.textSecondary,
    textAlign: TextAlign? = null,
    softWrap: Boolean = true
) {
    Text(
        text = text,
        style = ChiplessShadowStyle(
            style = ChiplessTypography.l2,
            offsetX = -6f,
            offsetY = 6f,
            blurRadius = 15f
        ),
        color = color,
        textAlign = textAlign,
        softWrap = softWrap
    )
}

@Composable
fun LargeFocusButtonText(
    text: String,
    color: Color = Color.Unspecified
) {
    Text(
        text = text,
        style = ChiplessShadowStyle(
            style = ChiplessTypography.h2,
            offsetX = -12f,
            offsetY = 12f,
            blurRadius = 25f
        ),
        color = color
    )
}

@Composable
fun LargeButtonText(
    text: String,
    color: Color = Color.Unspecified
) {
    Text(
        text = text,
        style = ChiplessShadowStyle(
            style = ChiplessTypography.sh1,
            offsetX = -8f,
            offsetY = 8f,
            blurRadius = 20f
        ),
        color = color
    )
}

@Composable
fun ActionButtonText(
    text: String,
    color: Color = Color.Unspecified
) {
    Text(
        text = text,
        style = ChiplessShadowStyle(
            style = ChiplessTypography.sh2,
            offsetX = -8f,
            offsetY = 8f,
            blurRadius = 20f
        ),
        color = color
    )
}
