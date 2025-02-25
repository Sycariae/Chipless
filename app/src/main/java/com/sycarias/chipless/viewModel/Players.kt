package com.sycarias.chipless.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf

class Players (playerCount: Int) {
    private val _players = listOf(*Array(playerCount) { Player() })

    val participatingIDs by derivedStateOf { // The list of players that are not sitting out or don't exist
        _players.indices.filter { playerID ->
            _players[playerID].name.isNotBlank() && _players[playerID].status != PlayerStatus.SAT_OUT
        }
    }
    val activeIDs by derivedStateOf { // The list of active players: players that can legally take a turn
        _players.indices.filter { playerID ->
            _players[playerID].name.isNotBlank() && _players[playerID].status !in listOf(
                PlayerStatus.FOLDED,
                PlayerStatus.SAT_OUT,
                PlayerStatus.ALL_IN
            )
        }
    }

    val highestBet = derivedStateOf {
        _players.maxByOrNull { it.currentBet }?.currentBet ?: 0
    }

    private val _focusID = mutableIntStateOf(0) // The focussed player, the one whose turn it is. By default, this is the first id from the list of active players
    val focusID by _focusID
    val focusPlayer by derivedStateOf { _players[focusID] }

    private val _dealerID = mutableIntStateOf(0)
    val dealerID by _dealerID
    val dealerPlayer by derivedStateOf { _players[dealerID] }

    // The active player ID of the player after the dealer
    val smallBlindPlayerID by getNextInActiveIDs(dealerID, increment = 1)
    val smallBlindPlayer by derivedStateOf { _players[smallBlindPlayerID] }
    // The active player ID of the player after the small blind player; 2nd after the dealer
    val bigBlindPlayerID by getNextInActiveIDs(dealerID, increment = 2)
    val bigBlindPlayer by derivedStateOf { _players[bigBlindPlayerID] }

    fun getNextInActiveIDs(
        playerID: Int,
        increment: Int = 1
    ): State<Int> {
        return derivedStateOf {
            if (activeIDs.isNotEmpty()) {
                activeIDs[( ( activeIDs.indexOf(playerID).takeIf { it != -1 } ?: activeIDs.first() ) + increment) % activeIDs.size]
            } else {
                -1 // Return an invalid player ID if no active players exist
            }
        }
    }
    fun getPlayer(playerID: Int): Player {
        return _players[playerID]
    }

    // Dealer Player Setter Function
    fun setDealerPlayer(playerID: Int) {
        _dealerID.intValue = playerID
    }

    // Focus Player Setter Functions
    private fun setFocusPlayer(playerID: Int) {
        _focusID.intValue = playerID
    }
    fun setInitialFocusPlayer() {
        if (activeIDs.isNotEmpty()) {
            setFocusPlayer(getNextInActiveIDs(playerID = dealerID, increment = 3).value)
        } else {
            throw IndexOutOfBoundsException("NO ACTIVE PLAYERS: activeIDs IS EMPTY")
        }
    }
    fun incrementFocusPlayer() {
        if (activeIDs.isNotEmpty()) {
            setFocusPlayer(getNextInActiveIDs(playerID = focusID, increment = 1).value)
        } else {
            throw IndexOutOfBoundsException("NO ACTIVE PLAYERS: activeIDs IS EMPTY")
        }
    }

    // = ELIMINATION
    fun checkAllForEliminations() {
        _players.forEach { player ->
            if (player.balance <= 0) {
                player.eliminate()
            }
        }
    }

    // Player Data Getter Functions
    fun getPlayerName(playerID: Int): State<String> {
        return derivedStateOf { _players[playerID].name }
    }
    fun getPlayerStatus(playerID: Int): State<PlayerStatus> {
        return derivedStateOf { _players[playerID].status }
    }
    fun getPlayerBalance(playerID: Int): State<Int> {
        return derivedStateOf { _players[playerID].balance }
    }
    fun getPlayerCurrentBet(playerID: Int): State<Int> {
        return derivedStateOf { _players[playerID].currentBet }
    }
    fun isActive(playerID: Int): Boolean {
        return playerID in activeIDs
    }
    fun isNotActive(playerID: Int): Boolean {
        return playerID !in activeIDs
    }

    // Player Data Setter Functions
    fun setPlayerName(playerID: Int, newName: String) {
        _players[playerID].name = newName
    }
    fun setPlayerStatus(playerID: Int, newStatus: PlayerStatus) {
        _players[playerID].status = newStatus
    }
    fun updateStatusesOnBet(betterID: Int, isRaise: Boolean) {
        // Update statuses for other participating players to PARTIAL_MATCH
        if (isRaise) {
            _players.forEach { player ->
                if (player.status == PlayerStatus.BET_MATCHED) {
                    player.status = PlayerStatus.PARTIAL_MATCH
                }
            }
        }

        // Update status for betting player to be BET_MATCHED or RAISED or ALL_IN
        setPlayerStatus(
            playerID = betterID,
            newStatus = if (isRaise) {
                PlayerStatus.RAISED
            } else {
                PlayerStatus.BET_MATCHED
            }
        )
    }
    fun placePlayerBet(playerID: Int, amount: Int) {
        _players[playerID].bet(amount)
    }
    fun payPlayer(playerID: Int, amount: Int) {
        _players[playerID].pay(amount)
    }
    fun setStartingBalances(startingChips: Int) {
        _players.forEach { player ->
            player.balance = startingChips
        }
    }

    // Reset Functions
    fun resetAllForNewRound() {
        _players.forEach { player ->
            player.resetForNewRound()
        }
    }
    fun resetAllForNewMatch() {
        _players.forEach { player ->
            player.resetForNewMatch()
        }
    }
    fun resetAllForNewTable() {
        _players.forEach { player ->
            player.resetForNewTable()
        }
    }
}