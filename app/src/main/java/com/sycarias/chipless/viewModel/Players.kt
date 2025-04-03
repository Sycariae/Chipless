package com.sycarias.chipless.viewModel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * The `Players` class manages a collection of [Player] objects within a game.
 * It manages [Player] turns, `dealer` assignment, blind players, filtered sub-lists of `_players` and preparing [Player] objects for new rounds, matches etc.
 *
 * @param playerCount The total number of players in the game. Must be at least 1. Max supported by UI is 10.
 *
 * @property _players A list of all [Player] objects. Only available in [Players]
 * @property list A list of all [Player] objects. Mirrors [_players].
 * @property participatingList A list of [Player] objects that are currently participating in the game (not sitting out).
 * @property activeList A list of [Player] objects that are currently active and able to take a turn.
 * @property bettingList A list of [Player] objects that have placed a bet (their [Player.currentBet] is greater than 0).
 * @property focus The [Player] object whose turn it is.
 * @property nextFocus The [Player] object who will have the next turn after the current [focus].
 * @property dealer The [Player] object designated as the dealer for the current round.
 * @property smallBlind The [Player] object who will place the small blind (the first active player after the dealer).
 * @property bigBlind The [Player] object who will place the big blind (the second active player after the dealer).
 *
 * @constructor Creates [_players] with a number of [Player] objects equal to `playerCount`.
 *              The first player in the list is initialized as the `dealer` and `focus`.
 */
class Players (playerCount: Int) {
    private val _players = mutableStateListOf(Player(isFocus = true, isDealer = true), *Array(playerCount-1) { Player() })
    val list = _players

    val participatingList by derivedStateOf { // The list of players that are not sitting out or don't exist
        _players.filter { it.isParticipating }
    }

    val activeList by derivedStateOf { // The list of active players: players that can legally take a turn
        _players.filter { it.isActive }
    }

    val bettingList by derivedStateOf { // The list of betting players: players that have a currentBet more than 0
        _players.filter { it.isBetting }
    }

    // The player whose turn it is
    var focus by mutableStateOf(_players[0])
        private set

    var nextFocus by mutableStateOf(_players[1])
        private set

    // The player who shuffles and deals the cards
    var dealer by mutableStateOf(_players[0]) // TODO: DETERMINE NEXT DEALER IN CASE DEALER GETS REMOVED FROM ACTIVE PLAYERS
        private set

    // The next active player after the dealer
    val smallBlind by derivedStateOf { nextActivePlayerAfter(dealer, increment = 1) }

    // The next active player after the small blind player, 2nd after the dealer
    val bigBlind by derivedStateOf { nextActivePlayerAfter(dealer, increment = 2) }


    // = BET MANAGEMENT
    /**
     * This function iterates through a list of [Player] objects and determines the highest
     * [Player.currentBet] value among them. If the list is empty or all players have a [Player.currentBet]
     * of `0`, it returns `0`.
     *
     * @param players A list of [Player] objects. Defaults to [_players].
     *
     * @return `Int`: The highest [Player.currentBet] placed by any [Player]. If no players have bets or the list is empty: `0`.
     */
    fun getHighestBet(players: List<Player> = _players): Int {
        return players.maxByOrNull { it.currentBet }?.currentBet ?: 0
    }

    /**
     * This function iterates through a list of [Player] objects and determines the lowest
     * [Player.currentBet] value among them. If the list is empty or all players have a [Player.currentBet]
     * of `0`, it returns `0`.
     *
     * @param players A list of [Player] objects. Defaults to [_players].
     *
     * @return `Int`: The lowest [Player.currentBet] placed by any [Player]. If no players have bets or the list is empty: `0`.
     */
    fun getLowestBet(players: List<Player> = _players): Int {
        return players.minByOrNull { it.currentBet }?.currentBet ?: 0
    }


    // = FOCUS AND DEALER SETTERS
    /**
     * Sets the [focus] player to the specified [Player] object.
     *
     * This function performs the following:
     * 1. Sets [Player.isFocus] for the previous [focus] player to `false`.
     * 2. Sets [focus] to the new [focus] player.
     * 3. Sets [Player.isFocus] for the new [focus] player to `true`.
     * 4. Sets [nextFocus] to the next active player after the new [focus].
     *
     * @param player The [Player] object to be set as the new [focus].
     */
    fun setFocusPlayer(player: Player) {
        focus.isFocus = false // Set previous player's focus state to false
        focus = player // Set new player as focus
        focus.isFocus = true // Set new player's focus state to true
        nextFocus = nextActivePlayerAfter(focus)
    }

    /**
     * Sets the [dealer] player to the specified [Player] object.
     *
     * This function performs the following:
     * 1. Sets [Player.isDealer] for the previous [dealer] player to `false`.
     * 2. Sets [dealer] to the new [dealer] player.
     * 3. Sets [Player.isDealer] for the new [dealer] player to `true`.
     *
     * @param player The [Player] object to be set as the new [dealer].
     */
    fun setDealerPlayer(player: Player) {
        dealer.isDealer = false // Set previous dealer's dealer state to false
        dealer = player // Set new player as dealer
        dealer.isDealer = true // Set new player's dealer state to true
    }


    // = ACTIVE PLAYER MANAGEMENT
    /**
     * Returns the next active player after the given [player] in a circular list.
     *
     * This function determines the next active player based on [player]
     * and an optional [increment]. It treats the [activeList] as a circular list,
     * meaning that after reaching the end, it wraps around to the beginning.
     *
     * @param player The current [Player]. This player must be present in [activeList].
     * @param increment The number of positions to advance in the [activeList]. Defaults to 1.
     *
     * @return The next [Player] after [player] in [activeList]. If [activeList] is empty, it returns the first [Player] in the [_players].
     */
    fun nextActivePlayerAfter(
        player: Player,
        increment: Int = 1
    ): Player {
        return if (activeList.isNotEmpty()) {
            activeList[ ( activeList.indexOf(player) + increment) % activeList.size ]
        } else {
            _players.first() // Return null if no active players exist
        }
    }


    // = FOCUS PLAYER MANAGEMENT
    /**
     * Sets the initial [focus] in a match.
     *
     * This function determines the [Player] who should have the initial focus
     * based on the current game state. It does this by finding the third active [Player] after the dealer.
     *
     * @see nextActivePlayerAfter
     * @see setFocusPlayer
     */
    fun setInitialFocusPlayer() {
        if (activeList.isNotEmpty()) {
            setFocusPlayer( nextActivePlayerAfter(dealer, increment = 3) )
        } else {
            throw IndexOutOfBoundsException("NO ACTIVE PLAYERS: activeIDs IS EMPTY")
        }
    }
    // TODO: Add function to set focus player on new round (1st after dealer)

    /**
     * Advances the [focus] to the next [Player].
     *
     * This function sets the [focus] to the [nextFocus], effectively cycling through the [activeList].
     *
     * @see setFocusPlayer
     */
    fun incrementFocusPlayer() {
        setFocusPlayer( nextFocus )
    }


    // = INITIATION
    /**
     * Sets the starting [Player.balance] for each [Player] at table initiation.
     *
     * This function iterates through all players in the [_players] list and assigns
     * them the specified [startingChips] as their initial [Player.balance].
     *
     * @param startingChips The number of chips each player will be given at the start of the table.
     */
    fun setStartingBalances(startingChips: Int) {
        _players.forEach { player ->
            player.balance = startingChips
        }
    }

    /**
     * Prepares each [Player] for a new round.
     *
     * Iterates through [_players] and sets [Player.currentBet] to 0, clearing any existing bets, for each [Player].
     * Iterates through [activeList] and sets [Player.status] to [PlayerStatus.IDLE], for each [Player].
     *
     * This is typically called at the start of a new round.
     */
    fun resetAllForNewRound() {
        _players.forEach { player ->
            player.currentBet = 0
        }
        activeList.forEach { player ->
            player.status = PlayerStatus.IDLE
        }
    }

    /**
     * Prepares each [Player] for a new match.
     *
     * Iterates through [_players] and sets [Player.currentBet] to 0, clearing any existing bets, for each [Player].
     * Iterates through [participatingList] and sets [Player.status] to [PlayerStatus.IDLE], for each [Player].
     *
     * This is typically called at the start of a new match, to ensure all players begin with a clean slate.
     */
    fun resetAllForNewMatch() {
        _players.forEach { player ->
            player.currentBet = 0
        }
        participatingList.forEach { player ->
            player.status = PlayerStatus.IDLE
        }
    }

    /**
     * Prepares each [Player] for a new table.
     *
     * This function iterates through all [Player] objects in [_players] and performs the following for each:
     *  - Sets [Player.currentBet] to 0, clearing any existing bets.
     *  - Sets [Player.status] to [PlayerStatus.IDLE], indicating they are ready to take their turn.
     *
     * This is typically called at the start of a new table, to ensure all players begin with a clean slate.
     */
    fun resetAllForNewTable() {
        _players.forEach { player ->
            player.currentBet = 0
            player.status = PlayerStatus.IDLE
        }
    }

    /**
     * Completely resets every [Player] in [_players].
     *
     * This function iterates through all [Player] objects in [_players] and calls [Player.reset].
     */
    fun reset() {
        _players.forEach { it.reset() }
    }
}