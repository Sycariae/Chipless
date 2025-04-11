package com.sycarias.chipless.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sycarias.chipless.ui.composables.PlayerTable
import com.sycarias.chipless.ui.composables.TableScreen
import com.sycarias.chipless.ui.composables.presets.ActionButton
import com.sycarias.chipless.ui.composables.presets.ActionButtonText
import com.sycarias.chipless.ui.composables.presets.Heading
import com.sycarias.chipless.ui.composables.presets.Subtitle
import com.sycarias.chipless.viewModel.BettingRound
import com.sycarias.chipless.viewModel.ViewModel
import kotlin.math.min

enum class PlayerActionType {
    CHECK,
    CALL,
    ALL_IN,
    BET,
    RAISE,
    FOLD
}

@Composable
fun GameTableScreen(navController: NavController, viewModel: ViewModel) {
    // View Model Variables
    val players = viewModel.players
    val bet = viewModel.bet

    val bettingRound by remember { derivedStateOf { viewModel.bettingRound } }
    val bettingRoundTitle: String = when(bettingRound) {
        BettingRound.PREFLOP -> "Pre-Flop"
        BettingRound.FLOP -> "Flop"
        BettingRound.TURN -> "Turn"
        BettingRound.RIVER -> "River"
        BettingRound.SHOWDOWN -> "Showdown"
    }

    LaunchedEffect(Unit) {
        viewModel.initialiseNewTable()
    }

    fun getBetAmountInput(): Int {
        /* TESTING START TODO: REMOVE TESTING */
        return min(100, players.focus.balance)
        /* TESTING END */
        /* TODO: Create Dialog to get validated bet amount */
    }

    fun getRaiseAmountInput(): Int {
        val defaultRaiseAmount = 100
        val maxRaiseAmount = players.focus.balance - (viewModel.currentTableBet - players.focus.currentBet)
        println("Max Raise Amount: $maxRaiseAmount")

        return if (maxRaiseAmount >= defaultRaiseAmount) {
            defaultRaiseAmount
        } else {
            maxRaiseAmount.coerceAtLeast(0) // Ensure it's not negative
        }
    }

    @Composable
    fun PlayerActionButton(
        type: PlayerActionType
    ) {
        ActionButton(
            onClick = {
                when (type) {
                    PlayerActionType.CHECK -> { bet.call(players.focus) }
                    PlayerActionType.CALL -> { bet.call(players.focus) }
                    PlayerActionType.ALL_IN -> { bet.allIn(players.focus) }
                    PlayerActionType.BET -> { bet.place(players.focus, getBetAmountInput(), isRaise = true) }
                    PlayerActionType.RAISE -> { bet.raise(players.focus, getRaiseAmountInput()) }
                    PlayerActionType.FOLD -> { players.focus.fold() }
                }
                players.incrementFocusPlayer()
                viewModel.checkForBettingRoundEnd()
            },
            contentPadding = PaddingValues(0.dp)
        ) {
            ActionButtonText(
                text = when (type) {
                    PlayerActionType.CHECK -> "Check"
                    PlayerActionType.CALL -> "Call"
                    PlayerActionType.ALL_IN -> "All In"
                    PlayerActionType.BET -> "Bet"
                    PlayerActionType.RAISE -> "Raise"
                    PlayerActionType.FOLD -> "Fold"
                }
            )
        }
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
        Subtitle(text = "${players.focus.name}'s Turn")

        Spacer(modifier = Modifier.height(35.dp))

        PlayerTable(
            players = players,
            screen = TableScreen.GAME
        )

        Spacer(modifier = Modifier.height(80.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 30.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlayerActionButton(
                type = if (viewModel.currentTableBet == players.focus.currentBet)
                    PlayerActionType.CHECK
                else if (viewModel.currentTableBet >= (players.focus.balance + players.focus.currentBet))
                    PlayerActionType.ALL_IN
                else
                    PlayerActionType.CALL
            )
            if (viewModel.currentTableBet < (players.focus.balance + players.focus.currentBet)) {
                PlayerActionButton(
                    type = if (viewModel.currentTableBet > 0)
                        PlayerActionType.RAISE
                    else
                        PlayerActionType.BET
                )
            }
            PlayerActionButton(
                type = PlayerActionType.FOLD
            )
        }
    }
}