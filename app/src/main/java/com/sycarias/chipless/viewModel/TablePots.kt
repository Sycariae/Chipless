package com.sycarias.chipless.viewModel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

class TablePots (players: Players) {
    val mainPot: Pot by mutableStateOf(Pot(includedPlayers = players.list))
    val sidePots: MutableList<Pot> = mutableListOf()
    val currentPot: Pot by derivedStateOf {
        sidePots.lastOrNull() ?: mainPot
    }

    // Creates a new Pot Object in SidePots list with the given includedPlayers
    fun newSidePot(startingAmount: Int = 0, includedPlayers: List<Player>) {
        sidePots.add(
            Pot(
                balance = startingAmount,
                includedPlayers = includedPlayers
            )
        )
    }

    // Sets the main pot balance to 0 and destroys all side pots
    fun resetPots() {
        mainPot.reset()
        sidePots.clear()
    }
}