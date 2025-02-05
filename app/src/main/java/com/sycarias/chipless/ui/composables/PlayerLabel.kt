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
import androidx.compose.ui.Modifier
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

@Composable
fun PlayerLabel(
    name: String = "",
    size: Dp = 50.dp,
    onClick: () -> Unit = {}
) {
    val cornerRadius = 100.dp
    val shape = RoundedCornerShape(cornerRadius)
    val color = ChiplessColors.primary
    val textStyle = ChiplessTypography.body
    val textWidth = measureTextWidth(text = name, style = textStyle)
    val textPadding = 15.dp
    val width = when {
        name.isEmpty() -> size // When Empty, default to this
        else -> textWidth + (textPadding * 2) // Text Width + Padding
    }
    val animatedWidth = animateDpAsState(targetValue = width, label = "Player Button Width")

    Button(
        onClick = onClick,
        modifier = Modifier
            .height(size)
            .width(animatedWidth.value)
            .border(
                width = (0.5).dp,
                shape = shape,
                color = color
            )
            .innerShadow(
                shape = shape,
                color = color.copy(alpha = 0.4f),
                offsetX = 0.dp,
                offsetY = 0.dp,
                blur = 22.dp
            )
            .innerShadow(
                shape = shape,
                color = color,
                offsetX = 0.dp,
                offsetY = 0.dp,
                blur = 8.dp
            )
            .buttonShadow(
                blurRadius = 35.dp,
                color = color.copy(alpha = 0.2f),
                cornerRadius = cornerRadius
            )
            .buttonShadow(
                blurRadius = 6.dp,
                color = color.copy(alpha = 0.5f),
                cornerRadius = cornerRadius
            ),
        shape = CircleShape,
        colors = ChiplessButtonColors(),
        contentPadding = PaddingValues(0.dp)
    ) {
        if ( name.isEmpty() ) {
            Icon(
                painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.icon_add)),
                contentDescription = "Add Icon",
                modifier = Modifier
                    .size(17.dp)
            )
        } else {
            Text(
                text = name,
                style = textStyle,
                modifier = Modifier.padding(start = textPadding, end = textPadding),
                softWrap = false
            )
        }
    }
}