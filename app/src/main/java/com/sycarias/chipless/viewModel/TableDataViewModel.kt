package com.sycarias.chipless.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TableDataViewModel: ViewModel() {
    // = TABLE CONFIG
    val tableConfig = TableConfig()


    // = PLAYERS
    val players = Players(10)


    // = TABLE BET AND POTS
    // The bet players must match for the current betting round
    val currentTableBet by derivedStateOf { players.getHighestBet() }
    // The running total of all bets made this match that'll be shared amongst the winners at the end
    val tablePots = TablePots(players)
    // Bet Management Class
    val bet = Bet(
        players = players,
        tableConfig = tableConfig,
        tablePots = tablePots,
        currentTableBet = derivedStateOf { currentTableBet }
    )


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
    // Called when a new betting round begins,
    fun initiateNewRound() {
        players.resetAllForNewRound() // Reset player statuses excluding FOLDED, ALL_IN and SAT_OUT and reset all current player bets
        incrementGameStage() // Set game stage to next stage from the current one
    }

    // Called after all betting rounds have been completed, resets currentBets, sets all participating players to IDLE status, resets pots and places blind bets
    fun initiateNewMatch() {
        players.resetAllForNewMatch() // Reset player statuses excluding SAT_OUT and all current player bets
        players.checkAllForEliminations() // Check every player and applies ELIMINATED status to those with a balance of 0
        tablePots.resetPots()
        resetBettingRound() // Set game stage to first stage: PREFLOP
        bet.placeBlinds()
    }

    // Called when first starting the table, performs all resets and ensures players are given correct starting balances
    fun initialiseNewTable() {
        players.resetAllForNewTable() // Reset all player statuses and reset all current player bets
        players.setStartingBalances(tableConfig.startingChips) // Set all player balances to startingChips
        players.setInitialFocusPlayer() // Set focus player to the 3rd player after the dealer
        tablePots.resetPots()
        resetBettingRound() // Set betting round to PREFLOP
        bet.placeBlinds()
    }
}