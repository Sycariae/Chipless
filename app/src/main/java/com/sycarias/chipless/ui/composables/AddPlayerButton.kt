package com.sycarias.chipless.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.extensions.innerShadow
import com.sycarias.chipless.ui.theme.ChiplessButtonColors
import com.sycarias.chipless.ui.theme.ChiplessColors

@Composable
fun AddPlayerButton(
    onClick: () -> Unit = {}
) {
    val shape = RoundedCornerShape(100.dp)
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(50.dp)
            .border(
                width = (0.5).dp,
                shape = shape,
                color = ChiplessColors.primary
            )
            .innerShadow(
                shape = shape,
                color = ChiplessColors.primary.copy(alpha = 0.4f),
                offsetX = 0.dp,
                offsetY = 0.dp,
                blur = 22.dp
            )
            .innerShadow(
                shape = shape,
                color = ChiplessColors.primary,
                offsetX = 0.dp,
                offsetY = 0.dp,
                blur = 8.dp
            ),
        shape = CircleShape,
        colors = ChiplessButtonColors(),
        elevation = ButtonDefaults.buttonElevation(15.dp),
        contentPadding = PaddingValues((16.5).dp)
    ) {
        Icon(
            painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.icon_add)),
            contentDescription = "Add Icon",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}