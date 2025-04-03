package com.sycarias.chipless.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue

/**
 * Used for managing a pool of chips that can be distributed to winning players.
 *
 * Maintains a [balance] and tracks the [includedPlayers] who have contributed to it.
 * It provides functionality to:
 * - Make deposits into the pot balance. | [deposit]
 * - Reset the pot balance. | [reset]
 *
 * @property balance The current balance of the [Pot]. Defaults to 0. Can only be changed by [deposit] and [reset].
 * @property includedPlayers The list of [Player] objects that have contributed to the [Pot].
 */
class Pot(
    balance: Int = 0,
    includedPlayers: List<Player> = listOf()
) {
    var balance by mutableIntStateOf(balance)
        private set
    val includedPlayers = mutableStateListOf(*includedPlayers.toTypedArray()) // TODO: Convert to immutable list data structure

    /**
     * Increase the [Pot]'s [balance] by the specified amount.
     *
     * @param amount The amount to deposit into the [Pot].
     */
    fun deposit(amount: Int) {
        balance += amount
    }

    /**
     * Resets the [Pot]'s [balance] to zero.
     */
    fun reset() {
        balance = 0
    }
}
