package com.sycarias.chipless.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue

class Bet(
    private val players: Players,
    private val tableConfig: TableConfig,
    private val tablePots: TablePots,
    currentTableBet: State<Int>,
) {
    private val currentTableBet: Int by currentTableBet

    private fun updateStatusesOnBet(bettingPlayer: Player, isRaise: Boolean) { // TODO: Replace isRaise with a newBettingPlayerStatus
        // Update statuses for other participating players to PARTIAL_MATCH
        players.list.forEach { player ->
            if (player.status in listOf(PlayerStatus.BET_MATCHED, PlayerStatus.RAISED) ) {
                player.status = PlayerStatus.PARTIAL_MATCH
            }
        }

        // Update status for betting player to be BET_MATCHED or RAISED | TODO: ...or ALL_IN
        bettingPlayer.status =
            if (isRaise) {
                PlayerStatus.RAISED
            } else {
                PlayerStatus.BET_MATCHED
            }
    }

    // Place a bet into the current pot and update player statuses
    fun place(player: Player, betAmount: Int, isRaise: Boolean = false) {
        tablePots.stagedBets += betAmount // Add bet amount to table pot and set table bet
        player.bet(betAmount) // Subtract bet amount from balance and add to currentBet for betting player
        updateStatusesOnBet(bettingPlayer = player, isRaise = isRaise) // Update player statuses: on raise, all BET_MATCHED are updated to PARTIAL_MATCH
    }

    // Place a bet equal to the current table bet
    fun call(player: Player) {
        place(player = player, betAmount = currentTableBet - player.currentBet, isRaise = false)
    }

    // Place a bet equal to the current table bet plus a raise amount
    fun raise(player: Player, raiseAmount: Int) {
        if (player.balance == ((currentTableBet + raiseAmount) - player.currentBet) ) {
            allIn(player = player, isRaise = true)
        } else {
            place(player = player, betAmount = (currentTableBet + raiseAmount) - player.currentBet, isRaise = true)
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