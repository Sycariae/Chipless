package com.sycarias.chipless.viewModel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TablePots (val players: Players) {
    val mainPot: Pot by mutableStateOf(Pot(includedPlayers = players.list))
    val sidePots: MutableList<Pot> = mutableListOf()
    val currentPot: Pot by derivedStateOf {
        sidePots.lastOrNull() ?: mainPot
    }

    // Stores bets pending distribution to table pots
    var stagedBets by mutableIntStateOf(0)

    fun commitBets() {
        var remainingStagedBets = stagedBets
        var remainingPlayers = players.bettingPlayers
        var previousBetAmount = 0

        while (remainingStagedBets != 0) {
            val currentBetAmount = players.getLowestBet(remainingPlayers)
            val betIncrement = currentBetAmount - previousBetAmount
            val betIncrementCollection = betIncrement * remainingPlayers.count()

            currentPot.deposit(betIncrementCollection)
            remainingStagedBets -= betIncrementCollection

            remainingPlayers = remainingPlayers.filter { it.currentBet > currentBetAmount }
            if (remainingPlayers.isNotEmpty()) { newSidePot(0, remainingPlayers) }
            previousBetAmount = currentBetAmount
        }
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

    // Distribute the a table pot amongst the winners
    fun distributePot(pot: Pot, winners: List<Player>) {
        val winningsSplit = pot.balance / winners.count() // Calculate an even split amongst the winners

        winners.forEach { player ->
            player.pay(winningsSplit) // Award each player their split
        }
    }

    // Sets the main pot balance to 0 and destroys all side pots
    fun resetPots() {
        mainPot.reset()
        sidePots.clear()
    }
}