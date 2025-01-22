package com.sycarias.chipless.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TableDataViewModel: ViewModel() {
    // = TABLE CONFIG
    // The starting balance given out at the start of a match
    private val _startingChips = mutableIntStateOf(1000)
    val startingChips: State<Int> = _startingChips
    // The minimum bet and the forced bet made by 2nd the player after the dealer at the start of a match
    private val _bigBlind = mutableIntStateOf(10)
    val bigBlind: State<Int> = _bigBlind
    // The forced bet made by the player after the dealer at the start of a match
    private val _smallBlind = mutableIntStateOf(5)
    val smallBlind: State<Int> = _smallBlind

    // Table Config Setter Functions
    fun setStartingChips(value: Int) {
        _startingChips.intValue = value
    }
    fun setBigBlind(value: Int) {
        _bigBlind.intValue = value
    }
    fun setSmallBlind(value: Int) {
        _smallBlind.intValue = value
    }


    // = PLAYERS
    private val _players by mutableStateOf(Players(10))
    val players: Players = _players


    // = BETS AND BALANCES
    // The bet players must match for the current betting round
    private val _currentTableBet = _players.highestBet
    val currentTableBet: State<Int> = _currentTableBet
    // The running total of all bets made this match that'll be shared amongst the winners at the end
    private val _tablePots by mutableStateOf(TablePots())
    val tablePot: TablePots = _tablePots

    // Bet Placement Helper Functions
    fun callBet(playerID: Int) {
        placeBet(playerID = playerID, betAmount = _currentTableBet.value, isRaise = false)
    }
    fun raiseBet(playerID: Int, raiseAmount: Int) {
        placeBet(playerID = playerID, betAmount = _currentTableBet.value + raiseAmount, isRaise = true)
    }
    fun allInBet(playerID: Int) {
        val isRaise = when { // Determine whether the ALL_IN bet is a raise or not
            _players.getPlayerBalance(playerID) > _currentTableBet.value -> true
            else -> false
        }

        placeBet(playerID = playerID, betAmount = _players.getPlayerBalance(playerID), isRaise = isRaise)

        _players.setPlayerStatus(playerID = playerID, status = PlayerStatus.ALL_IN)
    }
    fun placeForcedBets() {
        placeBet(playerID = _players.smallBlindPlayer, betAmount = _smallBlind.intValue, isRaise = false) // Place forced bet for small blind player
        placeBet(playerID = _players.bigBlindPlayer, betAmount = _bigBlind.intValue, isRaise = false) // Place forced bet for big blind player

        _players.setPlayerStatus(playerID = _players.smallBlindPlayer, status = PlayerStatus.PARTIAL_MATCH) // Ensure small blind player is given PARTIAL_MATCH status without making the big blind player RAISED status
    }
    private fun placeBet(playerID: Int, betAmount: Int, isRaise: Boolean) {
        _tablePots.makeDeposit(betAmount) // Add bet amount to table pot and set table bet
        _players.placePlayerBet(playerID = playerID, amount = betAmount) // Update bet amount and balance for betting player
        _players.updateStatusesOnBet(betterID = playerID, isRaise = isRaise) // Update player statuses: on raise, all BET_MATCHED are updated to PARTIAL_MATCH
    }

    // Distribute the table pot amongst the winners
    fun distributeWinnings(winningsAmount: Int, playerIDs: List<Int>) {
        val winningsSplit = winningsAmount / playerIDs.count() // Calculate an even split amongst the winners

        playerIDs.forEach { playerID ->
            _players.payPlayer(playerID = playerID, amount = winningsSplit) // Award each player their split
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
        _players.resetAllForNewRound() // Reset player statuses excluding FOLDED, ALL_IN and SAT_OUT and reset all current player bets
        incrementGameStage() // Set game stage to next stage from the current one
    }
    fun initiateNewMatch() {
        _players.resetAllForNewMatch() // Reset player statuses excluding SAT_OUT and all current player bets
        _players.checkAllForEliminations() // Check every player and applies ELIMINATED status to those with a balance of 0
        _tablePots.resetPots()
        resetBettingRound() // Set game stage to first stage: PREFLOP
    }
    // Ran when first starting the table, performs all resets and ensures players are given correct starting balances
    fun initialiseNewTable() {
        _players.resetAllForNewTable() // Reset all player statuses and reset all current player bets
        _players.setStartingBalances(_startingChips.intValue) // Set all player balances to startingChips
        _players.setInitialFocusPlayer() // Set focus player to the 3rd player after the dealer
        _tablePots.resetPots()
        resetBettingRound() // Set betting round to PREFLOP
    }
}