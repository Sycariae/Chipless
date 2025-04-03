package com.sycarias.chipless.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue

/**
 * Manages betting actions and status updates on bet placement.
 *
 * This class provides functionality for:
 * - [updateStatusesOnBet] - Updating player statuses based on bet actions.
 * - [place] - Placing bets and staging them in [tablePots].
 * - [call] - Calling the [currentTableBet].
 * - [raise] - Raising the [currentTableBet] by a specified amount.
 * - [allIn] - Going all-in.
 * - [placeBlinds] - Placing blinds at the start of a game.
 *
 * @property players [Players] | The object that manages the list of players in the game.
 * @property tableConfig [TableConfig] | The object that manages the configuration of the table.
 * @property tablePots [TablePots] | The object that manages the pots and staged bets.
 * @property currentTableBet The amount that all players must match to move to the next round of betting.
 */
class Bet(
    private val players: Players,
    private val tableConfig: TableConfig,
    private val tablePots: TablePots,
    currentTableBet: State<Int>,
) {
    private val currentTableBet: Int by currentTableBet

    /**
     * Updates the statuses of players in the current round of betting based on a new bet placed.
     *
     * This function performs two main actions:
     * 1. Updates player statuses [PlayerStatus.BET_MATCHED] and [PlayerStatus.RAISED], to [PlayerStatus.PARTIAL_MATCH].
     * 2. Updates the status of the `bettingPlayer` to [PlayerStatus.BET_MATCHED] or [PlayerStatus.RAISED], accordingly.
     *
     * @param bettingPlayer The [Player] who is making the bet.
     * @param isRaise `true` if the betting player raised the [currentTableBet]. `false` if they matched it.
     */
    private fun updateStatusesOnBet(bettingPlayer: Player, isRaise: Boolean) { // TODO: Replace isRaise with a newBettingPlayerStatus and update docs
        // Update statuses for other players to PARTIAL_MATCH
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

    /**
     * Places a bet for a player, stages the bet, and updates player statuses accordingly.
     *
     * 1. Adds `betAmount` to [TablePots.stagedBets].
     * 2. Deducts `betAmount` from [Player.balance] and adds it to [Player.currentBet].
     * 3. Updates player statuses via [updateStatusesOnBet]. If `isRaise` is true, the betting player's status is set to [PlayerStatus.RAISED]; otherwise, it's set to [PlayerStatus.BET_MATCHED].
     *
     * @param player The [Player] placing the bet.
     * @param betAmount The amount to be bet.
     * @param `true` if the betting player raised the [currentTableBet]. `false` if they matched it.
     */
    fun place(player: Player, betAmount: Int, isRaise: Boolean = false) {
        tablePots.stagedBets += betAmount // Add bet amount to table pot and set table bet
        player.bet(betAmount) // Subtract bet amount from balance and add to currentBet for betting player
        updateStatusesOnBet(bettingPlayer = player, isRaise = isRaise) // Update player statuses: on raise, all BET_MATCHED are updated to PARTIAL_MATCH
    }

    /**
     * Allows a player to call the current bet.
     *
     * Calling means matching the current table bet by adding the difference
     * between the [currentTableBet] and the [Player.currentBet] to the pot.
     *
     * @param player The player who is calling the [currentTableBet].
     */
    fun call(player: Player) {
        place(player = player, betAmount = currentTableBet - player.currentBet, isRaise = false)
    }

    /**
     * Places a bet equal to the [currentTableBet] plus the [raiseAmount].
     *
     * This function allows a player to raise the current bet. It calculates the
     * amount needed to call the raise and either places the bet or goes all-in
     * if the player's balance is not enough to cover the full raise.
     *
     * @param player The player who is raising the bet.
     * @param raiseAmount The amount by which the player wants to raise the bet.
     */
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