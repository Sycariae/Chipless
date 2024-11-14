package com.sycarias.chipless.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.extensions.buttonShadow
import com.sycarias.chipless.ui.theme.ChiplessButtonColors
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.ui.utils.HiddenRippleTheme

@Composable
fun DealerIcon(
    active: Boolean = true,
    size: Dp = 30.dp,
    onClick: () -> Unit = {}
) {
    CompositionLocalProvider(LocalRippleTheme provides HiddenRippleTheme) {
        val color = when {
            active -> ChiplessColors.primary
            else -> ChiplessColors.secondary.copy(alpha = 0.8f)
        }
        val buttonColors = ChiplessButtonColors(bg = color, fg = ChiplessColors.bgSecondary)

        fun Modifier.dealerIconDefaults(): Modifier {
            return this
                .width(size)
                .height(size)
                .clickable(onClick = onClick)
        }

        val modifier = when {
            active -> Modifier
                .buttonShadow(
                    blurRadius = 5.dp,
                    color = color.copy(alpha = 0.8f),
                    offsetX = 0.dp,
                    offsetY = 0.dp,
                    cornerRadius = 100.dp
                )
                .dealerIconDefaults()
            else -> Modifier
                .buttonShadow(
                    blurRadius = 5.dp,
                    color = ChiplessColors.secondary.copy(alpha = 0.75f),
                    offsetX = 0.dp,
                    offsetY = 0.dp,
                    cornerRadius = 100.dp
                )
                .dealerIconDefaults()
        }

        Button(
            modifier = modifier,
            shape = RoundedCornerShape(100.dp),
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