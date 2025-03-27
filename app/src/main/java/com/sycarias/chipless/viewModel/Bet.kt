package com.sycarias.chipless.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class Bet(
    private val players: Players,
    private val tableConfig: TableConfig,
    private val tablePots: TablePots,
) {
    private val currentTableBet by players.highestBet

    // Place a bet into the current pot and update player statuses
    fun place(player: Player, betAmount: Int, isRaise: Boolean) {
        tablePots.stagedBets += betAmount // Add bet amount to table pot and set table bet
        player.bet(betAmount) // Update bet amount and balance for betting player
        players.updateStatusesOnBet(bettingPlayer = player, isRaise = isRaise) // Update player statuses: on raise, all BET_MATCHED are updated to PARTIAL_MATCH
    }

    // Place a bet equal to the current table bet
    fun call(player: Player) {
        place(player = player, betAmount = currentTableBet, isRaise = false)
    }

    // Place a bet equal to the current table bet plus a raise amount
    fun raise(player: Player, raiseAmount: Int) {
        if (player.balance == (currentTableBet + raiseAmount)) {
            allIn(player = player, isRaise = true)
        } else {
            place(player = player, betAmount = currentTableBet + raiseAmount, isRaise = true)
        }
    }

    // Place a bet equal to player's balance
    fun allIn(player: Player, isRaise: Boolean = false) {
        place(player = player, betAmount = player.balance, isRaise = isRaise)
        player.status = PlayerStatus.ALL_IN // Replace the bet status applied by updateStatusesOnBet() with ALL_IN for the subject player
    }

    // Place blind bets (at the start of a match)
    fun placeBlinds() {
        place(player = players.smallBlind, betAmount = tableConfig.smallBlind, isRaise = false) // Place forced bet for small blind player
        place(player = players.bigBlind, betAmount = tableConfig.bigBlind, isRaise = false) // Place forced bet for big blind player

        players.smallBlind.status = PlayerStatus.PARTIAL_MATCH // Ensure small blind player is given PARTIAL_MATCH status without making the big blind player RAISED status
    }
}