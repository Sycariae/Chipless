package com.sycarias.chipless.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

/**
 * Configuration settings for the table.
 *
 * This class holds settings that define the initial state of the table,
 * such as the starting chips for each player, the big blind amount and the small blind amount.
 *
 * Also provides a function to reset these settings to their default values. | [reset]
 *
 * @property startingChips The starting balance given out to each player at the start of a match.
 * @property smallBlind The forced bet made by the player directly after the dealer at the start of a match.
 * @property bigBlind The minimum bet and the forced bet made by 2nd the player after the dealer at the start of a match.
 */
class TableConfig {
    // The starting balance given out at the start of a match
    var startingChips by mutableIntStateOf(1000)
    // The minimum bet and the forced bet made by 2nd the player after the dealer at the start of a match
    var bigBlind by mutableIntStateOf(10)
    // The forced bet made by the player after the dealer at the start of a match
    var smallBlind by mutableIntStateOf(5)

    /**
     * Resets table configuration settings to their default values.
     * This is typically called at the beginning of a new game or when the game needs to be restarted.
     *
     * Specifically:
     * - [startingChips] is set to 1000.
     * - [smallBlind] is set to 5.
     * - [bigBlind] is set to 10.
     */
    fun reset() {
        startingChips = 1000
        smallBlind = 5
        bigBlind = 10
    }
}