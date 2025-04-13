package com.sycarias.chipless.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.ui.composables.presets.Body
import com.sycarias.chipless.ui.composables.presets.Label
import com.sycarias.chipless.ui.theme.ChiplessColors
import kotlin.math.roundToInt

// TODO: Add disabled functionality and styling
@Composable
fun IntSlider(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange,
    modifier: Modifier = Modifier,
    stepSize: Int = 1,
    label: String = "",
    showValue: Boolean = false,
    onValueChangeFinished: (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (label.isNotEmpty()) {
            Row(horizontalArrangement = Arrangement.Start) { Label(text = label) }
            Spacer(modifier = Modifier.height(4.dp))
        }
        if (showValue) {
            Body(
                text = value.toString()
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
        Slider(
            value = value.toFloat(),
            onValueChange = {
                onValueChange(it.roundToInt())
            },
            valueRange = range.first.toFloat()..range.last.toFloat(),
            steps = ((range.last - range.first - 1) / stepSize).coerceAtLeast(0),
            onValueChangeFinished = onValueChangeFinished,
            colors = SliderDefaults.colors(
                thumbColor = ChiplessColors.primary,
                activeTrackColor = ChiplessColors.primary,
                inactiveTrackColor = ChiplessColors.secondary,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            )
        )
    }
}