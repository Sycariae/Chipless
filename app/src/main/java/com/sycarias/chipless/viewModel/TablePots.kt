package com.sycarias.chipless.viewModel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

class TablePots {
    val mainPot: Pot by mutableStateOf(Pot())
    val sidePots: MutableList<Pot> = mutableListOf()
    val currentPot: Pot by derivedStateOf {
        sidePots.lastOrNull() ?: mainPot
    }

    // Creates a new Pot Object in SidePots list with the given includedPlayers
    fun newSidePot(startingAmount: Int = 0, includedPlayerIDs: List<Int>) {
        sidePots.add(
            Pot(
                balance = startingAmount,
                includedPlayerIDs = includedPlayerIDs
            )
        )
    }

    // Sets the main pot balance to 0 and destroys all side pots
    fun resetPots() {
        mainPot.reset()
        sidePots.clear()
    }
}