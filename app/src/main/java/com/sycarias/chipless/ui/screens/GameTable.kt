package com.sycarias.chipless.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sycarias.chipless.ui.composables.presets.ActionButton
import com.sycarias.chipless.ui.composables.presets.ActionButtonText
import com.sycarias.chipless.ui.composables.presets.Heading
import com.sycarias.chipless.ui.composables.presets.Subtitle
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

    @Composable
    fun PlayerActionButton(
        onClick: () -> Unit,
        text: String
    ) {
        ActionButton(
            onClick = onClick
        ) { ActionButtonText(text = text) }
    }

    // START OF UI
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(35.dp))

        // Game Stage Heading
        Heading(text = bettingRoundTitle)

        Spacer(modifier = Modifier.height(6.dp))

        // Player Turn Subheading
        Subtitle(text = "$focusPlayerName's Turn")

        Spacer(modifier = Modifier.height(45.dp))

        ActionButton(
            onClick = { viewModel.initiateNewRound() }
        ) { ActionButtonText(text = "Next Round") }

        Spacer(modifier = Modifier.height(20.dp))

        ActionButton(
            onClick = { players.incrementFocusPlayer() }
        ) { ActionButtonText(text = "Next Player") }
    }
}