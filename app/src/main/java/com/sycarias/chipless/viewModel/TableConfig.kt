package com.sycarias.chipless.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

class TableConfig {
    // The starting balance given out at the start of a match
    var startingChips by mutableIntStateOf(1000)
    // The minimum bet and the forced bet made by 2nd the player after the dealer at the start of a match
    var bigBlind by mutableIntStateOf(10)
    // The forced bet made by the player after the dealer at the start of a match
    var smallBlind by mutableIntStateOf(5)

    fun reset() {
        startingChips = 1000
        bigBlind = 10
        smallBlind = 5
    }
}