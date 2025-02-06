package com.sycarias.chipless.ui.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    MEDIUM,
    LOW
}

@Composable
fun PlayerLabel(
    name: String = "",
    size: Dp = 50.dp,
    onClick: () -> Unit = {},
    chips: Int? = null,
    glowIntensity: GlowIntensity = GlowIntensity.MEDIUM,
    greyOut: Boolean = false,
    hideOnEmpty: Boolean = false,
) {
    val cornerRadius = 100.dp
    val shape = RoundedCornerShape(cornerRadius)
    val glowColor = ChiplessColors.primary
    val textStyle = ChiplessTypography.body
    val textWidth = measureTextWidth(text = name, style = textStyle)
    val textPadding = 15.dp
    val width = when {
        name.isEmpty() -> size // When Empty, default to this
        else -> textWidth + (textPadding * 2) // Text Width + Padding
    }
    val animatedWidth = animateDpAsState(targetValue = width, label = "Player Button Width")

    val borderColor = when (glowIntensity) {
        GlowIntensity.HIGH -> glowColor
        GlowIntensity.MEDIUM -> glowColor
        GlowIntensity.LOW -> Color.Transparent
    }
    fun getGlowOpacity(id: Int, isInnerShadow: Boolean): State<Float> {
        return derivedStateOf {
            when (glowIntensity) {
                GlowIntensity.HIGH -> if (isInnerShadow) { listOf(0.4f, 1f)[id] } else { listOf(0.4f, 0.5f)[id] }
                GlowIntensity.MEDIUM -> if (isInnerShadow) { listOf(0.4f, 1f)[id] } else { listOf(0.2f, 0.5f)[id] }
                GlowIntensity.LOW -> if (isInnerShadow) { listOf(0.25f, 0.4f)[id] } else { listOf(0.1f, 0.3f)[id] }
            }
        }
    }
    fun getGlowBlur(id: Int, isInnerShadow: Boolean): State<Dp> {
        return derivedStateOf {
            when (glowIntensity) {
                GlowIntensity.HIGH -> if (isInnerShadow) { listOf(26.dp, 12.dp)[id] } else { listOf(75.dp, 25.dp)[id] }
                GlowIntensity.MEDIUM -> if (isInnerShadow) { listOf(22.dp, 8.dp)[id] } else { listOf(35.dp, 6.dp)[id] }
                GlowIntensity.LOW -> if (isInnerShadow) { listOf(18.dp, 8.dp)[id] } else { listOf(35.dp, 6.dp)[id]}
            }
        }
    }

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
                    color = glowColor.copy(alpha = getGlowOpacity(0, isInnerShadow = true).value),
                    blur = getGlowBlur(0, isInnerShadow = true).value,
                    offsetX = 0.dp,
                    offsetY = 0.dp
                )
                .innerShadow(
                    shape = shape,
                    color = glowColor.copy(alpha = getGlowOpacity(1, isInnerShadow = true).value),
                    blur = getGlowBlur(1, isInnerShadow = true).value,
                    offsetX = 0.dp,
                    offsetY = 0.dp
                )
                .buttonShadow(
                    color = glowColor.copy(alpha = getGlowOpacity(0, isInnerShadow = false).value),
                    blurRadius = getGlowBlur(0, isInnerShadow = false).value,
                    cornerRadius = cornerRadius
                )
                .buttonShadow(
                    color = glowColor.copy(alpha = getGlowOpacity(1, isInnerShadow = false).value),
                    blurRadius = getGlowBlur(1, isInnerShadow = false).value,
                    cornerRadius = cornerRadius
                ),
            shape = CircleShape,
            colors = ChiplessButtonColors(),
            contentPadding = PaddingValues(0.dp)
        ) {
            if (name.isNotEmpty()) {
                Text(
                    text = name,
                    style = textStyle,
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