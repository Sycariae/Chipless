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
import com.sycarias.chipless.ui.composables.GlowIntensity
import com.sycarias.chipless.ui.composables.PlayerLabel
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

    val bettingRound by remember { viewModel.bettingRound }
    val bettingRoundTitle: String = when(bettingRound) {
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

        val playerNames by remember {
            derivedStateOf {
                listOf(
                    players.focusPlayer.name,
                    players.getPlayer(players.getNextInActiveIDs(players.focusID, increment = 1).value).name,
                    players.getPlayer(players.getNextInActiveIDs(players.focusID, increment = 2).value).name,
                    players.getPlayer(players.getNextInActiveIDs(players.focusID, increment = 3).value).name
                )
            }
        }

        ActionButton(
            onClick = { viewModel.initiateNewRound() },
            enabled = playerNames[0] != "Luke",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        ) { ActionButtonText(text = "Next Round") }

        Spacer(modifier = Modifier.height(20.dp))

        ActionButton(
            onClick = { players.incrementFocusPlayer() },
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        ) { ActionButtonText(text = "Next Player") }

        Spacer(modifier = Modifier.height(40.dp))

        PlayerLabel(
            name = playerNames[0],
            glowIntensity = if (playerNames[0] == players.dealerPlayer.name) { GlowIntensity.HIGH } else { GlowIntensity.LOW }
        )

        Spacer(modifier = Modifier.height(10.dp))

        PlayerLabel(
            name = playerNames[1],
            glowIntensity = if (playerNames[1] == players.dealerPlayer.name) { GlowIntensity.HIGH } else { GlowIntensity.LOW }
        )

        Spacer(modifier = Modifier.height(10.dp))

        PlayerLabel(
            name = playerNames[2],
            glowIntensity = if (playerNames[2] == players.dealerPlayer.name) { GlowIntensity.HIGH } else { GlowIntensity.LOW },
            greyedOut = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        PlayerLabel(
            name = if (bettingRound == BettingRound.FLOP) playerNames[3] else "",
            glowIntensity = if (playerNames[3] == players.dealerPlayer.name) { GlowIntensity.HIGH } else { GlowIntensity.LOW },
            hideOnEmpty = true
        )
    }
}