package com.sycarias.chipless.ui.composables

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
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.extensions.buttonShadow
import com.sycarias.chipless.ui.extensions.innerShadow
import com.sycarias.chipless.ui.theme.ChiplessButtonColors
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.ui.theme.ChiplessTypography
import com.sycarias.chipless.ui.utils.measureTextWidth

@Composable
fun PlayerButton(
    name: String = "",
    onClick: () -> Unit = {}
) {
    val cornerRadius = 100.dp
    val shape = RoundedCornerShape(cornerRadius)
    val color = ChiplessColors.primary
    val textStyle = ChiplessTypography.body
    val textWidth = measureTextWidth(text = name, style = textStyle)
    val width = when {
        name.isEmpty() -> 50.dp // When Empty, default to this
        else -> textWidth + 30.dp // Text Width + Padding
    }

    Button(
        onClick = onClick,
        modifier = Modifier
            .height(50.dp)
            .width(width)
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
                color = ChiplessColors.primary.copy(alpha = 0.2f),
                cornerRadius = cornerRadius,
                //redrawTrigger = { derivedStateOf { width } }
            )
            .buttonShadow(
                blurRadius = 6.dp,
                color = ChiplessColors.primary.copy(alpha = 0.5f),
                cornerRadius = cornerRadius,
                //redrawTrigger = { derivedStateOf { width } }
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
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )
        }
    }
}