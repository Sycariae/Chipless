package com.sycarias.chipless.viewModel

import androidx.compose.runtime.getValue

class Bet(
    private val players: Players,
    private val tableConfig: TableConfig,
    private val tablePots: TablePots,
) {
    private val currentTableBet by players.highestBet

    // Bet Placement Helper Functions
    fun call(playerID: Int) {
        place(playerID = playerID, betAmount = currentTableBet, isRaise = false)
    }
    fun raise(playerID: Int, raiseAmount: Int) {
        place(playerID = playerID, betAmount = currentTableBet + raiseAmount, isRaise = true)
    }
    fun allIn(playerID: Int) {
        val isRaise = when { // Determine whether the ALL_IN bet is a raise or not
            players.getPlayerBalance(playerID).value > currentTableBet -> true
            else -> false
        }

        place(playerID = playerID, betAmount = players.getPlayerBalance(playerID).value, isRaise = isRaise)

        players.setPlayerStatus(playerID = playerID, newStatus = PlayerStatus.ALL_IN)
    }
    fun placeBlinds() {
        place(playerID = players.smallBlindPlayerID, betAmount = tableConfig.smallBlind, isRaise = false) // Place forced bet for small blind player
        place(playerID = players.bigBlindPlayerID, betAmount = tableConfig.bigBlind, isRaise = false) // Place forced bet for big blind player

        players.setPlayerStatus(playerID = players.smallBlindPlayerID, newStatus = PlayerStatus.PARTIAL_MATCH) // Ensure small blind player is given PARTIAL_MATCH status without making the big blind player RAISED status
    }
    fun place(playerID: Int, betAmount: Int, isRaise: Boolean) {
        tablePots.currentPot.deposit(betAmount) // Add bet amount to table pot and set table bet
        players.placePlayerBet(playerID = playerID, amount = betAmount) // Update bet amount and balance for betting player
        players.updateStatusesOnBet(betterID = playerID, isRaise = isRaise) // Update player statuses: on raise, all BET_MATCHED are updated to PARTIAL_MATCH
    }
}