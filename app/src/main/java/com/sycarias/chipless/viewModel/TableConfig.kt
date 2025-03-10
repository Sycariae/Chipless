package com.sycarias.chipless.viewModel

import androidx.compose.runtime.mutableIntStateOf

class TableConfig {
    // The starting balance given out at the start of a match
    val startingChips = mutableIntStateOf(1000)
    // The minimum bet and the forced bet made by 2nd the player after the dealer at the start of a match
    val bigBlind = mutableIntStateOf(10)
    // The forced bet made by the player after the dealer at the start of a match
    val smallBlind = mutableIntStateOf(5)
}