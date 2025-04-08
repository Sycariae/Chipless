package com.sycarias.chipless.ui.extensions

import android.graphics.BlurMaskFilter
import android.graphics.Paint
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePaint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.dropShadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    cornerRadius: Dp = 0.dp
) = this.drawWithContent {

    // Recreate the shadow Paint whenever drawing is triggered
    val paint = Paint().apply {
        this.color = color.toArgb()
        if (blurRadius != 0.dp) {
            maskFilter = BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

        val left = offsetX.toPx()
        val top = offsetY.toPx()
        val right = size.width
        val bottom = size.height

    // Draw the shadow behind the main content
    drawIntoCanvas { canvas ->
        if (cornerRadius > 0.dp) {
            canvas.drawRoundRect(
                left,
                top,
                right + left,
                bottom + top,
                cornerRadius.toPx(),
                cornerRadius.toPx(),
                paint.asComposePaint()
            )
        } else {
            canvas.drawRect(
                left,
                top,
                right + left,
                bottom + top,
                paint.asComposePaint()
            )
        }
    }

    // Draw the main content on top of the shadow
    drawContent()
}