package com.sycarias.chipless.ui.composables

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.extensions.dropShadow
import com.sycarias.chipless.ui.theme.ChiplessColors

enum class PlayerStatusIcon {
    RAISED,
    BET_MATCHED,
    PARTIAL_MATCH
}

@Composable
fun StatusIcon(
    type: PlayerStatusIcon
) {
    val icon = when(type) {
        PlayerStatusIcon.RAISED -> ImageVector.vectorResource(id = R.drawable.icon_raised)
        PlayerStatusIcon.BET_MATCHED -> ImageVector.vectorResource(id = R.drawable.icon_matched)
        PlayerStatusIcon.PARTIAL_MATCH -> ImageVector.vectorResource(id = R.drawable.icon_partial_match)
    }
    val color = when(type) {
        PlayerStatusIcon.RAISED -> ChiplessColors.success
        PlayerStatusIcon.BET_MATCHED -> ChiplessColors.success
        PlayerStatusIcon.PARTIAL_MATCH -> ChiplessColors.warning
    }

    Icon(
        painter = rememberVectorPainter(image = icon),
        contentDescription = "Player Status Icon",
        modifier = Modifier
            .dropShadow(
                blurRadius = 5.dp,
                color = color,
                cornerRadius = 100.dp
            )
    )
}