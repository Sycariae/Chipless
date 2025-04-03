package com.sycarias.chipless.viewModel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


/**
 * Manages the distribution of bets into the main pot and side pots.
 *
 * This class handles the logic for creating and managing the [mainPot] and [sidePots]
 * based on player bets. It provides functionality to:
 * - Stage and commit bets | [stagedBets] and [commitBets].
 * - Create a new side pot when players go all-in. | [newSidePot]
 * - Distribute pots to winners. | [distributePot]
 * - Reset all pots and [stagedBets]. | [reset]
 *
 * @param players The [Players] object containing the list of players and their betting information.
 *
 * @property mainPot The main [Pot] of the game, containing a collection of bets made by all players.
 * @property sidePots A mutable list of [Pot] objects representing side pots, created when players go all-in.
 * The side pot includes all players who contributed to it and contains those contributions.
 * @property currentPot The current pot in play, either the last [Pot] in [sidePots] if there are any, or the [mainPot] if [sidePots] is empty.
 * @property stagedBets The total amount of bets currently staged for distribution to the [mainPot] and [sidePots] via [commitBets].
 */
class TablePots (val players: Players) {
    val mainPot: Pot by mutableStateOf(Pot(includedPlayers = players.list))
    val sidePots: MutableList<Pot> = mutableListOf()
    val currentPot: Pot by derivedStateOf {
        sidePots.lastOrNull() ?: mainPot
    }
    var stagedBets by mutableIntStateOf(0)

    /**
     * Commits [stagedBets] to the [currentPot] and creates [sidePots] if necessary.
     *
     * This function iteratively collects bet increments from players with [stagedBets],
     * adding them to the [currentPot]. Players who have bet more than the current lowest
     * bet are moved to a new side pot. This process repeats until all [stagedBets]
     * are committed or no players have bets to process.
     *
     * The function works by:
     * 1. Finding the lowest bet among remaining players.
     * 2. Collecting the increment needed from each player to reach that bet.
     * 3. Depositing the total into the [currentPot].
     * 4. Filtering out players who don't have any more [stagedBets].
     * 5. Creating a side pot with the filtered players via [newSidePot], if there is any remaining.
     * 6. Repeating until all [stagedBets] are committed.
     */
    fun commitBets() {
        var remainingStagedBets = stagedBets
        var remainingPlayers = players.bettingList.toMutableList()
        var previousBetAmount = 0

        while (remainingStagedBets > 0 && remainingPlayers.isNotEmpty()) {
            val currentBetAmount = players.getLowestBet(remainingPlayers)
            val betIncrement = currentBetAmount - previousBetAmount
            val betIncrementCollection = betIncrement * remainingPlayers.count()

            currentPot.deposit(betIncrementCollection)
            remainingStagedBets -= betIncrementCollection

            remainingPlayers = remainingPlayers.filter { it.currentBet > currentBetAmount }.toMutableList()
            if (remainingStagedBets > 0 && remainingPlayers.isNotEmpty()) { newSidePot(0, remainingPlayers) }
            previousBetAmount = currentBetAmount
        }
        stagedBets = 0
    }

    /**
     * Creates a new side pot and adds it to the list of side pots.
     *
     * This function generates a new [Pot] object and adds it to the list of side pots.
     * A side pot is created when one or more players are all-in with an amount less than
     * the current table bet.
     *
     * @param startingAmount The initial amount in the side pot. Defaults to 0. This represents
     *                       the sum of chips contributed by players who are all-in.
     * @param includedPlayers A list of [Player] objects that are eligible to win this side pot.
     *                        This includes all players who contributed to this side pot.
     */
    fun newSidePot(startingAmount: Int = 0, includedPlayers: List<Player>) {
        sidePots.add(
            Pot(
                balance = startingAmount,
                includedPlayers = includedPlayers
            )
        )
    }

    /**
     * Distributes the contents of a pot evenly among a list of winning players.
     *
     * This function takes a [Pot] object and a list of [Player] objects representing the [winners].
     * It calculates an even split of the [Pot]'s balance among the [winners] and then awards each
     * winner their share of the pot.
     *
     * @param pot The [Pot] object containing the balance to be distributed.
     * @param winners A list of [Player] objects representing the winners who will receive a share of the pot.
     */
    fun distributePot(pot: Pot, winners: List<Player>) {
        val winningsSplit = pot.balance / winners.count() // Calculate an even split amongst the winners

        winners.forEach { player ->
            player.pay(winningsSplit) // Award each player their split
        }
    }

    /**
     * Resets the game's pot system to its initial state.
     *
     * This function performs the following actions:
     * 1. Resets the [mainPot]'s balance to 0, clearing any accumulated bets.
     * 2. Clears [sidePots], destroying all existing [Pot] objects in the list.
     * 3. Resets the [stagedBets] amount to 0.
     *
     * This function is typically called at the beginning of a new match.
     */
    fun reset() {
        mainPot.reset()
        sidePots.clear()
        stagedBets = 0
    }
}