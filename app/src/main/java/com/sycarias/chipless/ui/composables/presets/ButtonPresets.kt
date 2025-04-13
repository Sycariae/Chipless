package com.sycarias.chipless.ui.composables.presets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.ui.extensions.dropShadow
import com.sycarias.chipless.ui.theme.ChiplessButtonColors
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.ui.utils.colorMix

// TODO: REWORK TO USE MULTIPLE OBJECTS WITH DEFAULT INPUTS AND A SINGLE COMPOSABLE (SIMILAR TO MATERIAL3's ButtonDefaults)

@Composable
fun LargeFocusButton(
    onClick: () -> Unit,
    contents: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(320.dp)
            .height(75.dp)
            .dropShadow(
                color = ChiplessColors.primary,
                offsetX = 0.dp,
                offsetY = 0.dp,
                blurRadius = 30.dp,
                cornerRadius = 100.dp
            ),
        shape = RoundedCornerShape(100.dp),
        colors = ChiplessButtonColors(ChiplessColors.primary)
    ) {
        contents()
    }
}

@Composable
fun LargeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(100.dp),
    color: Color = ChiplessColors.secondary,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(5.dp),
    contents: @Composable () -> Unit
) {
    Button(
        onClick = if (enabled) onClick else { {} },
        modifier = Modifier
            .width(290.dp)
            .height(65.dp)
            .then(modifier),
        shape = shape,
        colors = ChiplessButtonColors(if (enabled) color else colorMix(color, ChiplessColors.bgPrimary, 0.4f)),
        elevation = elevation
    ) {
        contents()
    }
}

@Composable
fun PrimaryActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = ChiplessColors.primary,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(5.dp),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    contents: @Composable () -> Unit
) {
    Button(
        onClick = if (enabled) onClick else { {} },
        modifier = Modifier
            .then(modifier)
            .height(55.dp)
            .then(
                if (enabled) {
                    Modifier.dropShadow(
                        color = ChiplessColors.primary,
                        offsetX = 0.dp,
                        offsetY = 0.dp,
                        blurRadius = 20.dp,
                        cornerRadius = 100.dp
                    )
                } else Modifier
            ),
        colors = if (enabled) {
            ChiplessButtonColors(color)
        } else ChiplessButtonColors(colorMix(ChiplessColors.secondary, ChiplessColors.bgPrimary, 0.4f), ChiplessColors.textTertiary),
        elevation = elevation,
        contentPadding = contentPadding
    ) {
        contents()
    }
}

@Composable
fun ActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(100.dp),
    color: Color = ChiplessColors.secondary,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(5.dp),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    contents: @Composable () -> Unit
) {
    Button(
        onClick = if (enabled) onClick else { {} },
        modifier = Modifier
            .width(100.dp)
            .height(50.dp)
            .then(modifier),
        shape = shape,
        colors = if (enabled) {
            ChiplessButtonColors(color)
        } else ChiplessButtonColors(colorMix(color, ChiplessColors.bgPrimary, 0.4f), ChiplessColors.textTertiary),
        elevation = elevation,
        contentPadding = contentPadding
    ) {
        contents()
    }
}