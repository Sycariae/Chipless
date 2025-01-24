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

    private val _focusID = mutableIntStateOf(-1) // The focussed player, the one whose turn it is. By default, this is the first id from the list of active players
    val focusID by _focusID

    private val _dealerID = mutableIntStateOf(-1)
    val dealerID by _dealerID

    // The index within the list of active player IDs for the dealer player ID
    private val dealerIndexInActiveIDs by derivedStateOf {
        activeIDs.find { it == _dealerID.intValue } ?: activeIDs.first()
    }

    // The active player ID of the player after the dealer
    val smallBlindPlayer by derivedStateOf {
        activeIDs.indexOfFirst { it == (dealerIndexInActiveIDs + 1) }
    }
    // The active player ID of the player after the small blind player; 2nd after the dealer
    val bigBlindPlayer by derivedStateOf {
        activeIDs.indexOfFirst { it == (dealerIndexInActiveIDs + 2) }
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
        setFocusPlayer(
            activeIDs.indexOfFirst { it == (dealerIndexInActiveIDs + 3) }
        )
    }
    fun incrementFocusPlayer() {
        val nextPlayerID = activeIDs.indexOfFirst { it > _focusID.intValue }

        setFocusPlayer (
            if (nextPlayerID != -1) {
                activeIDs[nextPlayerID]
            } else {
                activeIDs.firstOrNull() ?: -1
            }
        )
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

    // Player Data Setter Functions
    fun setPlayerName(playerID: Int, name: String) {
        players[playerID].name = name
    }
    fun setPlayerStatus(playerID: Int, status: PlayerStatus) {
        players[playerID].status = status
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
            status = if (isRaise) {
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