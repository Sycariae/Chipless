package com.sycarias.chipless.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.sycarias.chipless.viewModel.Player

@Composable
fun PlayerLabelRow(
    spacing: Dp,
    leftPlayer: Player,
    rightPlayer: Player,
    size: Dp,
    screen: TableScreen
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(size),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row (
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            PlayerLabel(
                player = leftPlayer,
                size = size,
                screen = screen
            )
        }
        Spacer(modifier = Modifier.width(spacing))
        Row (
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Start
        ) {
            PlayerLabel(
                player = rightPlayer,
                size = size,
                screen = screen
            )
        }
    }
}