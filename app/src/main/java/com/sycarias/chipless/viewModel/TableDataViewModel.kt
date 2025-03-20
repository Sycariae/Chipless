package com.sycarias.chipless.viewModel

import androidx.compose.runtime.State
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
    val currentTableBet by players.highestBet
    // The running total of all bets made this match that'll be shared amongst the winners at the end
    val tablePots = TablePots()

    // Bet Placement Helper Functions
    fun callBet(playerID: Int) {
        placeBet(playerID = playerID, betAmount = currentTableBet, isRaise = false)
    }
    fun raiseBet(playerID: Int, raiseAmount: Int) {
        placeBet(playerID = playerID, betAmount = currentTableBet + raiseAmount, isRaise = true)
    }
    fun allInBet(playerID: Int) {
        val isRaise = when { // Determine whether the ALL_IN bet is a raise or not
            players.getPlayerBalance(playerID).value > currentTableBet -> true
            else -> false
        }

        placeBet(playerID = playerID, betAmount = players.getPlayerBalance(playerID).value, isRaise = isRaise)

        players.setPlayerStatus(playerID = playerID, newStatus = PlayerStatus.ALL_IN)
    }
    fun placeForcedBets() {
        placeBet(playerID = players.smallBlindPlayerID, betAmount = tableConfig.smallBlind, isRaise = false) // Place forced bet for small blind player
        placeBet(playerID = players.bigBlindPlayerID, betAmount = tableConfig.bigBlind, isRaise = false) // Place forced bet for big blind player

        players.setPlayerStatus(playerID = players.smallBlindPlayerID, newStatus = PlayerStatus.PARTIAL_MATCH) // Ensure small blind player is given PARTIAL_MATCH status without making the big blind player RAISED status
    }
    private fun placeBet(playerID: Int, betAmount: Int, isRaise: Boolean) {
        tablePots.currentPot.deposit(betAmount) // Add bet amount to table pot and set table bet
        players.placePlayerBet(playerID = playerID, amount = betAmount) // Update bet amount and balance for betting player
        players.updateStatusesOnBet(betterID = playerID, isRaise = isRaise) // Update player statuses: on raise, all BET_MATCHED are updated to PARTIAL_MATCH
    }

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
        // TODO: Deduct Blind Bets
    }
    // Ran when first starting the table, performs all resets and ensures players are given correct starting balances
    fun initialiseNewTable() {
        players.resetAllForNewTable() // Reset all player statuses and reset all current player bets
        players.setStartingBalances(tableConfig.startingChips) // Set all player balances to startingChips
        players.setInitialFocusPlayer() // Set focus player to the 3rd player after the dealer
        tablePots.resetPots()
        resetBettingRound() // Set betting round to PREFLOP
        // TODO: Deduct Blind Bets
    }
}