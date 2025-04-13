package com.sycarias.chipless.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.extensions.dropShadow
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.viewModel.PlayerStatus

enum class PlayerIconType {
    RAISED,
    BET_MATCHED,
    PARTIAL_MATCH,
    DEALER;

    companion object {
        fun fromStatus(status: PlayerStatus): PlayerIconType {
            return when (status) {
                PlayerStatus.IDLE -> BET_MATCHED
                PlayerStatus.BET_MATCHED -> BET_MATCHED
                PlayerStatus.PARTIAL_MATCH -> PARTIAL_MATCH
                PlayerStatus.RAISED -> RAISED
                PlayerStatus.FOLDED -> BET_MATCHED
                PlayerStatus.SAT_OUT -> BET_MATCHED
                PlayerStatus.ALL_IN -> BET_MATCHED
            }
        }
    }
}

@Composable
fun PlayerStatusIcon(
    type: PlayerIconType,
    modifier: Modifier = Modifier
) {
    val icon = when(type) {
        PlayerIconType.RAISED -> R.drawable.icon_arrow_up
        PlayerIconType.BET_MATCHED -> R.drawable.icon_tick
        PlayerIconType.PARTIAL_MATCH -> R.drawable.icon_subtract
        PlayerIconType.DEALER -> R.drawable.icon_dealer
    }
    val color = when(type) {
        PlayerIconType.RAISED -> ChiplessColors.success
        PlayerIconType.BET_MATCHED -> ChiplessColors.success
        PlayerIconType.PARTIAL_MATCH -> ChiplessColors.warning
        PlayerIconType.DEALER -> ChiplessColors.primary
    }

    Box(
        modifier = Modifier
            .then(modifier)
            .background(color = color, shape = CircleShape)
            .dropShadow(
                blurRadius = 5.dp,
                color = color,
                cornerRadius = 100.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = rememberVectorPainter(image = ImageVector.vectorResource(id = icon)),
            tint = ChiplessColors.bgPrimary,
            contentDescription = "Player Status Icon",
            modifier = Modifier
                .fillMaxSize()
                .padding((2.5).dp)
        )
    }
}