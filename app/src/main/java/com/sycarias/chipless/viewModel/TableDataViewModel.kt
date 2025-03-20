package com.sycarias.chipless.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TableDataViewModel: ViewModel() {
    // = TABLE CONFIG
    val tableConfig = TableConfig()

    // = PLAYERS
    val players = Players(10)

    // = TABLE BET AND POTS
    // The bet players must match for the current betting round
    val currentTableBet = players.highestBet
    // The running total of all bets made this match that'll be shared amongst the winners at the end
    val tablePots = TablePots()
    // Bet Management Class
    val bet = Bet(players, tableConfig, tablePots)

    // Distribute the table pot amongst the winners
    fun distributeWinnings(winningsAmount: Int, playerIDs: List<Int>) {
        val winningsSplit = winningsAmount / playerIDs.count() // Calculate an even split amongst the winners

        playerIDs.forEach { playerID ->
            players.payPlayer(playerID = playerID, amount = winningsSplit) // Award each player their split
        }
    }


    // = BETTING ROUND
    // The current round of betting ( e.g. PREFLOP, FLOP, TURN... )
    private val _bettingRound = mutableStateOf(BettingRound.PREFLOP)
    val bettingRound: State<BettingRound> = _bettingRound

    // Update betting round to PREFLOP
    private fun resetBettingRound() {
        _bettingRound.value = BettingRound.PREFLOP
    }
    // Update betting round to next round
    private fun incrementGameStage() {
        _bettingRound.value = when(_bettingRound.value) {
            BettingRound.PREFLOP -> BettingRound.FLOP
            BettingRound.FLOP -> BettingRound.TURN
            BettingRound.TURN -> BettingRound.RIVER
            BettingRound.RIVER -> BettingRound.SHOWDOWN
            BettingRound.SHOWDOWN -> BettingRound.PREFLOP
        }
    }


    // = INITIALISATION
    fun initiateNewRound() {
        players.resetAllForNewRound() // Reset player statuses excluding FOLDED, ALL_IN and SAT_OUT and reset all current player bets
        incrementGameStage() // Set game stage to next stage from the current one
    }
    fun initiateNewMatch() {
        players.resetAllForNewMatch() // Reset player statuses excluding SAT_OUT and all current player bets
        players.checkAllForEliminations() // Check every player and applies ELIMINATED status to those with a balance of 0
        tablePots.resetPots()
        resetBettingRound() // Set game stage to first stage: PREFLOP
        bet.placeBlinds()
    }
    // Ran when first starting the table, performs all resets and ensures players are given correct starting balances
    fun initialiseNewTable() {
        players.resetAllForNewTable() // Reset all player statuses and reset all current player bets
        players.setStartingBalances(tableConfig.startingChips) // Set all player balances to startingChips
        players.setInitialFocusPlayer() // Set focus player to the 3rd player after the dealer
        tablePots.resetPots()
        resetBettingRound() // Set betting round to PREFLOP
        bet.placeBlinds()
    }
}