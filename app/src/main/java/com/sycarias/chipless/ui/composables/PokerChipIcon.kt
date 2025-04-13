package com.sycarias.chipless.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.extensions.dropShadow
import com.sycarias.chipless.ui.theme.ChiplessColors

@Composable
fun PokerChipIcon(
    size: Dp = 24.dp,
) {
    Image(
        painter = painterResource(id = R.drawable.image_chip),
        contentDescription = "Poker Chip Icon",
        modifier = Modifier
            .size(size)
            .dropShadow(
                color = ChiplessColors.primary,
                blurRadius = (size / 3).coerceIn(range = 5.dp..20.dp),
                cornerRadius = 100.dp
            )
    )
}