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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MoveRight
import com.sycarias.chipless.ui.composables.InputDialog
import com.sycarias.chipless.ui.composables.IntSlider
import com.sycarias.chipless.ui.composables.PlayerTable
import com.sycarias.chipless.ui.composables.TableScreen
import com.sycarias.chipless.ui.composables.presets.ActionButton
import com.sycarias.chipless.ui.composables.presets.ActionButtonText
import com.sycarias.chipless.ui.composables.presets.Body
import com.sycarias.chipless.ui.composables.presets.Heading
import com.sycarias.chipless.ui.composables.presets.Subtitle
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.viewModel.BettingRound
import com.sycarias.chipless.viewModel.ViewModel
import kotlinx.coroutines.launch

enum class PlayerActionType {
    CHECK,
    CALL,
    ALL_IN,
    BET,
    RAISE,
    FOLD
}

enum class BetDialogType {
    BET,
    RAISE
}

@Composable
fun GameTableScreen(navController: NavController, viewModel: ViewModel) {
    // = VIEWMODEL OBJECTS
    val players = viewModel.players
    val bet = viewModel.bet

    // = BETTING ROUND
    val bettingRound by remember { derivedStateOf { viewModel.bettingRound } }
    val bettingRoundTitle: String = when(bettingRound) {
        BettingRound.PREFLOP -> "Pre-Flop"
        BettingRound.FLOP -> "Flop"
        BettingRound.TURN -> "Turn"
        BettingRound.RIVER -> "River"
        BettingRound.SHOWDOWN -> "Showdown"
    }

    // = BETTING DIALOG
    var showBetDialog by remember { mutableStateOf(false) }
    var betDialogType by remember { mutableStateOf(BetDialogType.BET) }

    // = DEBOUNCE SYSTEM
    var isButtonProcessing by remember { mutableStateOf(false) }
    val buttonCoroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        viewModel.initialiseNewTable()
    }

    @Composable
    fun BetInputDialog(
        type: BetDialogType,
    ) {
        var value by remember {
            mutableIntStateOf(
                if (type == BetDialogType.BET) bet.minBetAmount
                else bet.minRaiseAmount
            )
        }
        val title = when (type) {
            BetDialogType.BET -> "Bet"
            BetDialogType.RAISE -> "Raise"
        }
        val label = when (type) {
            BetDialogType.BET -> "Bet Amount"
            BetDialogType.RAISE -> "Raise Amount"
        }

        InputDialog(
            title = title,
            confirmText = "Place",
            onDismiss = {
                showBetDialog = false
            },
            onConfirm = {
                when (type) {
                    BetDialogType.BET -> bet.place(players.focus, value)
                    BetDialogType.RAISE -> bet.raise(players.focus, value)
                }
                players.incrementFocusPlayer()
                viewModel.checkForBettingRoundEnd()

                showBetDialog = false
            },
        ) {
            IntSlider(
                value = value,
                onValueChange = { value = it },
                range = if (type == BetDialogType.BET) bet.betAmountRange else bet.raiseAmountRange,
                stepSize = 5,
                label = label,
                showValue = true
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Body(text = players.focus.balance.toString())
                Icon(
                    painter = rememberVectorPainter(image = Lucide.MoveRight),
                    contentDescription = "Right Arrow",
                    tint = ChiplessColors.textSecondary,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
                Body(
                    text = when (type) {
                        BetDialogType.BET -> (players.focus.balance - value).toString()
                        BetDialogType.RAISE -> (players.focus.balance - (value + bet.callAmount)).toString()
                    }
                )
            }
        }
    }

    @Composable
    fun PlayerActionButton(
        type: PlayerActionType
    ) {
        ActionButton(
            onClick = {
                if (isButtonProcessing || showBetDialog) return@ActionButton
                isButtonProcessing = true
                buttonCoroutineScope.launch {
                    try {
                        when (type) {
                            PlayerActionType.CHECK -> { bet.call(players.focus) }
                            PlayerActionType.CALL -> { bet.call(players.focus) }
                            PlayerActionType.ALL_IN -> { bet.allIn(players.focus) }
                            PlayerActionType.BET -> {
                                betDialogType = BetDialogType.BET
                                showBetDialog = true
                            }
                            PlayerActionType.RAISE -> {
                                betDialogType = BetDialogType.RAISE
                                showBetDialog = true
                            }
                            PlayerActionType.FOLD -> { players.focus.fold() }
                        }
                        if (type !in listOf(PlayerActionType.BET, PlayerActionType.RAISE)) {
                            players.incrementFocusPlayer()
                            viewModel.checkForBettingRoundEnd()
                        }
                    } finally {
                        isButtonProcessing = false
                    }
                }
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
    if (showBetDialog) {
        BetInputDialog(type = betDialogType)
    }
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