package com.sycarias.chipless.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.extensions.dropShadow
import com.sycarias.chipless.ui.theme.ChiplessButtonColors
import com.sycarias.chipless.ui.theme.ChiplessColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealerIcon(
    active: Boolean = true,
    size: Dp = 30.dp,
    alpha: Float = 1f,
    onClick: () -> Unit = {}
) {
    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        val color = when {
            active -> ChiplessColors.primary
            else -> ChiplessColors.secondary.copy(alpha = 0.8f)
        }
        val glowColor = when {
            active -> ChiplessColors.primary.copy(alpha = 0.8f)
            else -> ChiplessColors.secondary.copy(alpha = 0.6f)
        }
        val buttonColors = ChiplessButtonColors(bg = color, fg = ChiplessColors.bgSecondary)
        val shape = RoundedCornerShape(100.dp)

        Button(
            modifier = Modifier
                .dropShadow(
                    blurRadius = 10.dp,
                    color = glowColor.copy(alpha = alpha),
                    offsetX = 0.dp,
                    offsetY = 0.dp,
                    cornerRadius = 100.dp
                )
                .width(size)
                .height(size)
                .graphicsLayer(alpha = alpha, shape = shape),
            shape = shape,
            colors = buttonColors,
            contentPadding = PaddingValues(start = (2.5).dp),
            onClick = onClick
        ) {
            Icon(
                painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.icon_dealer)),
                contentDescription = "Dealer Icon",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}