package com.sycarias.chipless.viewModel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

class TablePots {
    private val mainPot by mutableStateOf(Pot())
    private val sidePots: MutableList<Pot> = mutableListOf()
    private val currentPot: Pot by derivedStateOf {
        sidePots.lastOrNull() ?: mainPot
    }

    // Pot Getter Functions
    fun getMainPotBalance(): Int {
        return mainPot.getBalance()
    }
    fun getSidePotBalance(sidePotID: Int): Int {
        return sidePots[sidePotID].getBalance()
    }
    fun getCurrentPotBalance(): Int {
        return currentPot.getBalance()
    }

    // Creates a new Pot Object in SidePots list with the given includedPlayers
    fun newSidePot(includedPlayerIDs: List<Int>) {
        sidePots.add(
            Pot(includedPlayerIDs = includedPlayerIDs)
        )
    }

    // Adds the given amount to the current pot
    fun makeDeposit(amount: Int) {
        currentPot.deposit(amount)
    }

    // Sets the main pot balance to 0 and destroys all side pots
    fun resetPots() {
        mainPot.reset()
        sidePots.clear()
    }
}