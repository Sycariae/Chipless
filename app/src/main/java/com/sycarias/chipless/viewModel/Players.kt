package com.sycarias.chipless.viewModel

import androidx.compose.runtime.*

class Players (playerCount: Int) {
    private val players = mutableStateListOf(*Array(playerCount) { Player() })

    val participatingIDs by derivedStateOf { // The list of players that are not sitting out or don't exist
        players.indices.filter { playerID ->
            players[playerID].name.isNotBlank() && players[playerID].status != PlayerStatus.SAT_OUT
        }
    }
    val activeIDs by derivedStateOf { // The list of active players: players that can legally take a turn
        players.indices.filter { playerID ->
            players[playerID].name.isNotBlank() && players[playerID].status !in listOf(
                PlayerStatus.FOLDED,
                PlayerStatus.SAT_OUT,
                PlayerStatus.ALL_IN
            )
        }
    }

    val highestBet = derivedStateOf {
        players.maxByOrNull { it.currentBet }?.currentBet ?: 0
    }

    private val _focusID = mutableIntStateOf(0) // The focussed player, the one whose turn it is. By default, this is the first id from the list of active players
    val focusID by _focusID
    val focusPlayer by derivedStateOf { players[_focusID.intValue] }

    private val _dealerID = mutableIntStateOf(0)
    val dealerID by _dealerID
    val dealerPlayer by derivedStateOf { players[_dealerID.intValue] }

    // The index within the list of active player IDs for the dealer player ID
    private val dealerIndexInActiveIDs by derivedStateOf {
        activeIDs.indexOf(_dealerID.intValue).takeIf { it != -1 } ?: activeIDs.firstOrNull() ?: -1
    }
    // The index within the list of active player IDs for the focus player ID
    private val focusIndexInActiveIDs by derivedStateOf {
        activeIDs.indexOf(_focusID.intValue).takeIf { it != -1 } ?: activeIDs.firstOrNull() ?: -1
    }
    // The index within the list of active player IDs for the next focus player ID
    private val nextFocusIndexInActiveIDs by derivedStateOf {
        if (activeIDs.isNotEmpty()) {
            activeIDs[(focusIndexInActiveIDs + 1) % activeIDs.size]
        } else {
            -1 // Return an invalid player ID if no active players exist
        }
    }

    // The active player ID of the player after the dealer
    val smallBlindPlayerID by derivedStateOf {
        if (activeIDs.isNotEmpty()) {
            activeIDs[(dealerIndexInActiveIDs + 1) % activeIDs.size]
        } else {
            -1 // Return an invalid player ID if no active players exist
        }
    }
    // The active player ID of the player after the small blind player; 2nd after the dealer
    val bigBlindPlayerID by derivedStateOf {
        if (activeIDs.isNotEmpty()) {
            activeIDs[(dealerIndexInActiveIDs + 2) % activeIDs.size]
        } else {
            -1 // Return an invalid player ID if no active players exist
        }
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
            setFocusPlayer(activeIDs[(dealerIndexInActiveIDs + 3) % activeIDs.size])
        } else {
            throw IndexOutOfBoundsException("NO ACTIVE PLAYERS: activeIDs IS EMPTY")
        }
    }
    fun incrementFocusPlayer() {
        if (activeIDs.isNotEmpty()) {
            setFocusPlayer(nextFocusIndexInActiveIDs)
        } else {
            throw IndexOutOfBoundsException("NO ACTIVE PLAYERS: activeIDs IS EMPTY")
        }
    }

    // = ELIMINATION
    fun checkAllForEliminations() {
        players.forEach { player ->
            if (player.balance <= 0) {
                player.eliminate()
            }
        }
    }

    // Player Data Getter Functions
    fun getPlayerName(playerID: Int): State<String> {
        return derivedStateOf { players[playerID].name }
    }
    fun getPlayerStatus(playerID: Int): State<PlayerStatus> {
        return derivedStateOf { players[playerID].status }
    }
    fun getPlayerBalance(playerID: Int): State<Int> {
        return derivedStateOf { players[playerID].balance }
    }
    fun getPlayerCurrentBet(playerID: Int): State<Int> {
        return derivedStateOf { players[playerID].currentBet }
    }
    fun isActive(playerID: Int): Boolean {
        return playerID in activeIDs
    }
    fun isNotActive(playerID: Int): Boolean {
        return playerID !in activeIDs
    }

    // Player Data Setter Functions
    fun setPlayerName(playerID: Int, newName: String) {
        players[playerID].name = newName
    }
    fun setPlayerStatus(playerID: Int, newStatus: PlayerStatus) {
        players[playerID].status = newStatus
    }
    fun updateStatusesOnBet(betterID: Int, isRaise: Boolean) {
        // Update statuses for other participating players to PARTIAL_MATCH
        if (isRaise) {
            players.forEach { player ->
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
        players[playerID].bet(amount)
    }
    fun payPlayer(playerID: Int, amount: Int) {
        players[playerID].pay(amount)
    }
    fun setStartingBalances(startingChips: Int) {
        players.forEach { player ->
            player.balance = startingChips
        }
    }

    // Reset Functions
    fun resetAllForNewRound() {
        players.forEach { player ->
            player.resetForNewRound()
        }
    }
    fun resetAllForNewMatch() {
        players.forEach { player ->
            player.resetForNewMatch()
        }
    }
    fun resetAllForNewTable() {
        players.forEach { player ->
            player.resetForNewTable()
        }
    }
}