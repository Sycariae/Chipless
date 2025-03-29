package com.sycarias.chipless.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.viewModel.Players

enum class TableScreen {
    GAME,
    CREATE
}

@Composable
fun PlayerTable(
    players: Players,
    screen: TableScreen,
    modifier: Modifier = Modifier
) {
    // Define Sizing and Spacing for Player Labels
    val playerLabelSize = 50.dp // Sizing of Player Labels
    val playerLabelVSpacing = 30.dp/*32.dp*/ // Player Label Vertical Spacing
    val playerLabelMidHSpacing = 155.dp/*175.dp*/ // Player Label Middle Horizontal Spacing
    val playerLabelTBHSpacing = 88.dp // Player Label Top and Bottom Row Horizontal Spacing

    // = UI START
    Box(
        modifier = modifier.then(Modifier.fillMaxSize()),
        contentAlignment = Alignment.Center
    ) {
        // TABLE IMAGE
        StaticShadow(
            blurRadius = 120.dp,
            color = ChiplessColors.primary.copy(alpha = 0.2f)
        ) {
            StaticShadow(
                blurRadius = 50.dp,
                color = ChiplessColors.primary.copy(alpha = 0.3f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_table),
                    contentDescription = "Poker Table Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(360.dp/*378.dp*/)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val rows = 1..5 // Range from 1 to total number of rows
            for (row in rows) {
                PlayerLabelRow(
                    spacing = if (row in listOf(rows.first, rows.last)) playerLabelTBHSpacing else playerLabelMidHSpacing,
                    leftPlayer = players.list[10-row],
                    rightPlayer = players.list[row-1],
                    size = playerLabelSize,
                    screen = screen
                )
                if (row != rows.last) { Spacer(modifier = Modifier.height(playerLabelVSpacing)) }
            }
        }
    }
}