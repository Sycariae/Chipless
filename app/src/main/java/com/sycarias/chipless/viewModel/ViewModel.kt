package com.sycarias.chipless.viewModel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * The ViewModel class manages the state and logic behind the entire app's functionality.
 *
 * It interacts with various classes to provide functionality for the table configuration,
 * players, betting, pot management and round progression.
 *
 * @property tableConfig [TableConfig] | The object that manages the configuration of the table.
 * @property players [Players] | The object that manages the list of players in the game.
 * @property currentTableBet The amount that all players must match to move to the next round of betting.
 * @property tablePots [TablePots] | The object that manages the pots and staged bets.
 * @property bet [Bet] | The object that manages betting actions and status updates upon betting.
 * @property bettingRound The current round of betting ( e.g. PREFLOP, FLOP, TURN... )
 */
class ViewModel: ViewModel() {
    val tableConfig = TableConfig()

    val players = Players(10)

    val currentTableBet by derivedStateOf { players.getHighestBet() }

    val tablePots = TablePots(players)

    val bet = Bet(
        players = players,
        tableConfig = tableConfig,
        tablePots = tablePots,
        currentTableBet = derivedStateOf { currentTableBet }
    )

    var bettingRound by mutableStateOf(BettingRound.PREFLOP) // TODO: Remove Showdown and make a function to display showdown with winner selection after RIVER
        private set

    // = BETTING ROUND MANAGEMENT

    /**
     * Resets the betting round.
     * Sets the [bettingRound] to [BettingRound.PREFLOP], called at the start of a new match.
     */
    private fun resetBettingRound() {
        bettingRound = BettingRound.PREFLOP
    }

    /**
     * Advances [bettingRound] to the next [BettingRound]:
     * `PREFLOP` -> `FLOP` -> `TURN` -> `RIVER` -> `SHOWDOWN` -> `PREFLOP` (repeats).
     */
    private fun incrementBettingRound() {
        bettingRound = when(bettingRound) {
            BettingRound.PREFLOP -> BettingRound.FLOP
            BettingRound.FLOP -> BettingRound.TURN
            BettingRound.TURN -> BettingRound.RIVER
            BettingRound.RIVER -> BettingRound.SHOWDOWN
            BettingRound.SHOWDOWN -> BettingRound.PREFLOP
        }
    }

    /**
     * Checks if the current betting round has ended.
     *
     * A betting round ends if:
     * 1. **[Players.activeList] is empty (everyone is either [PlayerStatus.FOLDED] or [PlayerStatus.ALL_IN]):** Proceeds to showdown.
     * 2. **All in [Players.activeList] are [PlayerStatus.BET_MATCHED] or [PlayerStatus.RAISED]:** Proceeds to the next betting round or showdown.
     */
    fun checkForBettingRoundEnd() {
        if (players.activeList.isEmpty()) {
            tablePots.commitBets()
            bettingRound = BettingRound.SHOWDOWN // TODO: Replace this with showdown function call
        }
        else if (
            players.activeList.all { it.status in listOf(PlayerStatus.BET_MATCHED, PlayerStatus.RAISED) }
        ) {
            initiateNewRound()
            players.setFocusPlayer(players.smallBlind) // TODO: Replace after adding a function in Players to set focus player on new round (1st after dealer)
        }
    }


    // = INITIALISATION

    /**
     * Initiates a new round of the match.
     *
     * This function makes preparations for the next round by:
     * 1. **Commits Bets:** Distributes the currently staged bets to the appropriate pots within [TablePots].
     * 2. **Resets Players:** For each player, resets their status to [PlayerStatus.IDLE] (except those who are [PlayerStatus.SAT_OUT]) and resets their [Player.currentBet].
     * 3. **Advances Betting Round:** Increments [bettingRound] to the next [BettingRound].
     *
     * @see TablePots
     * @see PlayerStatus
     * @see Player
     * @see Players
     */
    fun initiateNewRound() {
        tablePots.commitBets() // Distribute the stagedBets to tablePots
        players.resetAllForNewRound() // Reset player statuses excluding FOLDED, ALL_IN and SAT_OUT and reset all current player bets
        incrementBettingRound() // Set game stage to next stage from the current one
    }

    /**
     * Initiates a new match.
     *
     * This function makes preparations for the next match by:
     * 1. **Resets Players:** For each player, resets their status to [PlayerStatus.IDLE] (except those who are [PlayerStatus.SAT_OUT]) and resets their [Player.currentBet].
     * 2. **Sets Focus Player:** Sets the focus player to the 3rd player after the dealer.
     * 3. **Resets Pots:** Clears the [TablePots.mainPot] and [TablePots.sidePots].
     * 4. **Resets Betting Round:** Sets [bettingRound] to [BettingRound.PREFLOP].
     * 5. **Places Blinds:** Automatically places the blind bets to start the new match.
     *
     * @see PlayerStatus
     * @see Player
     * @see BettingRound
     */
    fun initiateNewMatch() {
        players.resetAllForNewMatch() // Reset player statuses excluding SAT_OUT and all current player bets
        players.setInitialFocusPlayer() // Set focus player to the 3rd player after the dealer
        tablePots.reset()
        resetBettingRound() // Set game stage to first stage: PREFLOP
        bet.placeBlinds()
    }

    /**
     * Initiates a new table.
     *
     * This function makes preparations for the next table by:
     * 1. **Resets Players:** For each player, resets their status to [PlayerStatus.IDLE] (except those who are [PlayerStatus.SAT_OUT]) and resets their [Player.currentBet].
     * 2. **Sets Starting Balances:** Sets all [Player.balance] to [TableConfig.startingChips].
     * 3. **Sets Focus Player:** Sets [Players.focus] to the 3rd player after the dealer.
     * 4. **Resets Pots:** Clears the [TablePots.mainPot] and [TablePots.sidePots].
     * 5. **Resets Betting Round:** Sets [bettingRound] to [BettingRound.PREFLOP].
     * 6. **Places Blinds:** Automatically places the blind bets to start the new table.
     *
     * @see PlayerStatus
     * @see Player
     * @see TableConfig
     * @see BettingRound
     */
    fun initialiseNewTable() {
        players.resetAllForNewTable() // Reset all player statuses and reset all current player bets
        players.setStartingBalances(tableConfig.startingChips) // Set all player balances to startingChips
        players.setInitialFocusPlayer() // Set focus player to the 3rd player after the dealer
        tablePots.reset()
        resetBettingRound() // Set betting round to PREFLOP
        bet.placeBlinds()
    }

    /**
     * Resets all areas of the [ViewModel] for configuring a new table.
     * This is for when navigating to the `CreateTableScreen`.
     *
     * Resets included in this function:
     * - [Players.reset]
     * - [TableConfig.reset]
     * - [TablePots.reset]
     * - [resetBettingRound]
     */
    fun resetForTableConfiguration() {
        players.reset()
        tableConfig.reset()
        tablePots.reset()
        resetBettingRound()
    }
}