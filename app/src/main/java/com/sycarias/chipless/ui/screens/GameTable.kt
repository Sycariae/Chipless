package com.sycarias.chipless.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.ui.theme.ChiplessShadowStyle
import com.sycarias.chipless.ui.theme.ChiplessTypography
import com.sycarias.chipless.viewModel.BettingRound
import com.sycarias.chipless.viewModel.TableDataViewModel

@Composable
fun GameTableScreen(navController: NavController, viewModel: TableDataViewModel) {
    // View Model Variables
    val startingChips by remember { viewModel.startingChips }
    val bigBlind by remember { viewModel.bigBlind }
    val smallBlind by remember { viewModel.smallBlind }

    val players = viewModel.players
    val focusPlayerName by remember { derivedStateOf { players.focusPlayer.name } }

    val gameStage by remember { viewModel.bettingRound }
    val bettingRoundTitle: String = when(gameStage) {
        BettingRound.PREFLOP -> "Pre-Flop"
        BettingRound.FLOP -> "Flop"
        BettingRound.TURN -> "Turn"
        BettingRound.RIVER -> "River"
        BettingRound.SHOWDOWN -> "Showdown"
    }

    // START OF UI
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(45.dp))

        // Game Stage Title
        Text(
            text = bettingRoundTitle,
            style = ChiplessShadowStyle(
                style = ChiplessTypography.h1,
                offsetX = -12f,
                offsetY = 12f,
                blurRadius = 25f
            ),
            color = ChiplessColors.textPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))

        // Player Turn Subtitle
        Text(
            text = "$focusPlayerName's Turn",
            style = ChiplessShadowStyle(
                style = ChiplessTypography.h2,
                offsetX = -12f,
                offsetY = 12f,
                blurRadius = 25f
            ),
            color = ChiplessColors.textSecondary
        )
    }
}