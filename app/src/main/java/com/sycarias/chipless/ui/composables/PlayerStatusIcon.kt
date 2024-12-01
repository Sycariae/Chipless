package com.sycarias.chipless.ui.composables

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.extensions.buttonShadow
import com.sycarias.chipless.ui.theme.ChiplessColors

@Composable
fun StatusIcon(
    type: StatusIconType
) {
    val icon = when(type) {
        StatusIconType.RAISE -> ImageVector.vectorResource(id = R.drawable.icon_raised)
        StatusIconType.BET_MET -> ImageVector.vectorResource(id = R.drawable.icon_matched)
        StatusIconType.PARTIALLY_MET -> ImageVector.vectorResource(id = R.drawable.icon_partial_match)
    }
    val color = when(type) {
        StatusIconType.RAISE -> ChiplessColors.success
        StatusIconType.BET_MET -> ChiplessColors.success
        StatusIconType.PARTIALLY_MET -> ChiplessColors.warning
    }

    Icon(
        painter = rememberVectorPainter(image = icon),
        contentDescription = "Player Status Icon",
        modifier = Modifier
            .buttonShadow(
                blurRadius = 5.dp,
                color = color,
                cornerRadius = 100.dp
            )
    )
}

enum class StatusIconType {
    RAISE,
    BET_MET,
    PARTIALLY_MET
}