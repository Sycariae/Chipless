package com.sycarias.chipless.backend

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.buttonShadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    cornerRadius: Dp = 0.dp
) = this.then(
    Modifier.drawBehind {
        val paint = android.graphics.Paint().apply {
            this.color = color.toArgb() // Convert Jetpack Compose Color to ARGB for Paint object
            if (blurRadius != 0.dp) {
                maskFilter = android.graphics.BlurMaskFilter(
                    blurRadius.toPx(),
                    android.graphics.BlurMaskFilter.Blur.NORMAL
                )
            }
        }

        val left = offsetX.toPx()
        val top = offsetY.toPx()
        val right = size.width
        val bottom = size.height

        val canvas = drawContext.canvas.nativeCanvas

        // Draw rounded or normal rect based on cornerRadius
        if (cornerRadius > 0.dp) {
            canvas.drawRoundRect(
                left,
                top,
                right + left,
                bottom + top,
                cornerRadius.toPx(),
                cornerRadius.toPx(),
                paint
            )
        } else {
            canvas.drawRect(
                left,
                top,
                right + left,
                bottom + top,
                paint
            )
        }
    }
)