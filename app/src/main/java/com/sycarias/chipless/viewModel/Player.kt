package com.sycarias.chipless.viewModel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Represents a player in a game, managing their name, status, balance, and betting.
 *
 * @param isFocus Initial focus state of the [Player]. Defaults to false.
 * @param isDealer Initial dealer state of the [Player]. Defaults to false.
 *
 * @property name The name of the [Player].
 * @property status The current [PlayerStatus] of the [Player].
 * @property balance The amount of chips the [Player] is in possession of.
 * @property currentBet The amount of chips the [Player] has bet in the current betting round.
 * @property isFocus Whether the [Player] is in focus (their turn).
 * @property isNotFocus Whether the [Player] is not in focus (not their turn).
 * @property isDealer Whether the [Player] is the dealer of the match.
 * @property isNotDealer Whether the [Player] is not the dealer of the match.
 * @property isParticipating Whether the [Player] is participating in the current round.
 * @property isNotParticipating Whether the [Player] is not participating in the current round.
 * @property isActive Whether the [Player] is active in the current round.
 * @property isNotActive Whether the [Player] is not active in the current round.
 * @property isBetting Whether the [Player] is betting in the current betting round.
 * @property isNotBetting Whether the [Player] is not betting in the current betting round.
 * @property isEliminated Whether the [Player] is eliminated from the game.
 * @property isNotEliminated Whether the [Player] is not eliminated from the game.
 *
 * @see pay
 * @see bet
 * @see fold
 * @see reset
 */
class Player(
    isFocus: Boolean = false,
    isDealer: Boolean = false
) {
    var name: String by mutableStateOf("")
    var status: PlayerStatus by mutableStateOf(PlayerStatus.IDLE)
    var balance: Int by mutableIntStateOf(1)
    var currentBet: Int by mutableIntStateOf(0)


    // = FOCUS CHECK
    var isFocus: Boolean by mutableStateOf(isFocus)
    val isNotFocus: Boolean by derivedStateOf { !isFocus }


    // = DEALER CHECK
    var isDealer: Boolean by mutableStateOf(isDealer)
    val isNotDealer: Boolean by derivedStateOf { !isDealer }


    // = PARTICIPATING CHECK
    val isParticipating: Boolean by derivedStateOf {
        name.isNotBlank() && isNotEliminated && status !in listOf(
            PlayerStatus.SAT_OUT
        )
    }
    val isNotParticipating: Boolean by derivedStateOf { !isParticipating }


    // = ACTIVE CHECK
    val isActive: Boolean by derivedStateOf {
        name.isNotBlank() && isNotEliminated && status !in listOf(
            PlayerStatus.FOLDED,
            PlayerStatus.SAT_OUT,
            PlayerStatus.ALL_IN
        )
    }
    val isNotActive: Boolean by derivedStateOf { !isActive }


    // = BETTING CHECK
    val isBetting: Boolean by derivedStateOf { name.isNotBlank() && currentBet > 0 }
    val isNotBetting: Boolean by derivedStateOf { !isBetting }


    // = ELIMINATION CHECK
    val isEliminated: Boolean by derivedStateOf { balance == 0 && status != PlayerStatus.ALL_IN }
    val isNotEliminated: Boolean by derivedStateOf { !isEliminated }


    // = BALANCE AND BET MANAGEMENT
    /**
     * Increases the [Player]'s [balance] by the specified `amount`.
     *
     * @param amount The amount to be added to the [Player]'s [balance].
     */
    fun pay(amount: Int) {
        balance += amount
    }

    /**
     * Places a bet of the specified amount.
     *
     * This function increases the [currentBet] by the given `amount` and deducts the same `amount` from the [Player]'s [balance].
     *
     * @param amount The amount to placed as a bet.
     */
    fun bet(amount: Int) {
        currentBet += amount
        balance -= amount
    }


    // = STATUS MANAGEMENT
    /**
     * Marks the [Player] as [PlayerStatus.FOLDED], withdrawing them from the current round.
     *
     * @see PlayerStatus
     */
    fun fold() {
        status = PlayerStatus.FOLDED
    }


    // = RESET

    /**
     * Resets the player's state to its initial values. Usually used when preparing for table configuration.
     *
     * This function clears:
     * - The [Player]'s [name]
     * - Sets the [Player]'s [status] to [PlayerStatus.IDLE],
     * - Sets the [Player]'s [balance] to `1`
     * - Clears the [Player]'s [currentBet], setting it to `0`.
     *
     * @see PlayerStatus
     */
    fun reset() {
        name = ""
        status = PlayerStatus.IDLE
        balance = 1
        currentBet = 0
    }
}
