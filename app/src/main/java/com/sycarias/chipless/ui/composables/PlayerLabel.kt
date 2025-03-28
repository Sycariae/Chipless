package com.sycarias.chipless.ui.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.extensions.buttonShadow
import com.sycarias.chipless.ui.extensions.innerShadow
import com.sycarias.chipless.ui.theme.ChiplessButtonColors
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.ui.theme.ChiplessTypography
import com.sycarias.chipless.ui.utils.measureTextWidth

enum class GlowIntensity {
    HIGH,
    NORMAL,
    LOW
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerLabel(
    name: String = "",
    size: Dp = 50.dp,
    onClick: () -> Unit = {},
    chips: Int? = null, // TODO: Add chips display
    glowIntensity: GlowIntensity = GlowIntensity.NORMAL,
    greyedOut: Boolean = false,
    hideOnEmpty: Boolean = false,
) {
    val cornerRadius = 100.dp
    val shape = RoundedCornerShape(cornerRadius)
    val glowColor = ChiplessColors.primary
    val textStyle = ChiplessTypography.body
    val textColor = if (greyedOut) ChiplessColors.textTertiary else ChiplessColors.textPrimary
    val textWidth = measureTextWidth(text = name, style = textStyle)
    val textPadding = 15.dp
    val width = when {
        name.isEmpty() -> size // When Empty, default to this
        else -> textWidth + (textPadding * 2) // Text Width + Padding
    }
    val animatedWidth = animateDpAsState(targetValue = width, label = "Player Button Width")

    val borderColor =
        if (!greyedOut) {
            when (glowIntensity) {
                GlowIntensity.HIGH -> glowColor
                GlowIntensity.NORMAL -> glowColor
                GlowIntensity.LOW -> Color.Transparent
            }
        } else Color.Transparent

    fun getGlowOpacity(id: Int): State<Float> {
        return derivedStateOf {
            if (!greyedOut) {
                when (glowIntensity) {
                    GlowIntensity.HIGH -> listOf(0.4f, 1f, 0.4f, 0.5f)[id]
                    GlowIntensity.NORMAL -> listOf(0.4f, 1f, 0.2f, 0.5f)[id]
                    GlowIntensity.LOW -> listOf(0.25f, 0.4f, 0.1f, 0.3f)[id]
                }
            } else 0f
        }
    }
    fun getGlowBlur(id: Int): State<Dp> {
        return derivedStateOf {
            if (!greyedOut) {
                when (glowIntensity) {
                    GlowIntensity.HIGH -> listOf(26.dp, 12.dp, 75.dp, 25.dp)[id]
                    GlowIntensity.NORMAL -> listOf(22.dp, 8.dp, 35.dp, 6.dp)[id]
                    GlowIntensity.LOW -> listOf(18.dp, 8.dp, 35.dp, 6.dp)[id]
                }
            } else 0.dp
        }
    }

    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        if (!(name.isEmpty() && hideOnEmpty)) {
            Button(
                onClick = onClick,
                modifier = Modifier
                    .height(size)
                    .width(animatedWidth.value)
                    .border(
                        width = (0.5).dp,
                        shape = shape,
                        color = borderColor
                    )
                    .innerShadow(
                        shape = shape,
                        color = glowColor.copy(alpha = getGlowOpacity(0).value),
                        blur = getGlowBlur(0).value,
                        offsetX = 0.dp,
                        offsetY = 0.dp
                    )
                    .innerShadow(
                        shape = shape,
                        color = glowColor.copy(alpha = getGlowOpacity(1).value),
                        blur = getGlowBlur(1).value,
                        offsetX = 0.dp,
                        offsetY = 0.dp
                    )
                    .buttonShadow(
                        color = glowColor.copy(alpha = getGlowOpacity(2).value),
                        blurRadius = getGlowBlur(2).value,
                        cornerRadius = cornerRadius
                    )
                    .buttonShadow(
                        color = glowColor.copy(alpha = getGlowOpacity(3).value),
                        blurRadius = getGlowBlur(3).value,
                        cornerRadius = cornerRadius
                    ),
                shape = CircleShape,
                colors = ChiplessButtonColors(if (greyedOut) ChiplessColors.greyOut else ChiplessColors.secondary),
                contentPadding = PaddingValues(0.dp)
            ) {
                if (name.isNotEmpty()) {
                    Text(
                        text = name,
                        style = textStyle,
                        color = textColor,
                        modifier = Modifier.padding(start = textPadding, end = textPadding),
                        softWrap = false
                    )
                } else {
                    Icon(
                        painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.icon_add)),
                        contentDescription = "Add Icon",
                        modifier = Modifier
                            .size(17.dp)
                    )
                }
            }
        }
    }
}