package com.sycarias.chipless.viewModel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Players (playerCount: Int) {
    private val _players = listOf(*Array(playerCount) { Player() })
    val list = _players

    val participatingPlayers by derivedStateOf { // The list of players that are not sitting out or don't exist
        _players.filter { player ->
            player.name.isNotBlank() && player.status != PlayerStatus.SAT_OUT
        }
    }

    val activePlayers by derivedStateOf { // The list of active players: players that can legally take a turn
        _players.filter { player ->
            player.name.isNotBlank() && player.status !in listOf(
                PlayerStatus.FOLDED,
                PlayerStatus.SAT_OUT,
                PlayerStatus.ALL_IN
            )
        }
    }

    val highestBet = derivedStateOf {
        _players.maxByOrNull { it.currentBet }?.currentBet ?: 0
    }

    // The player whose turn it is
    var focus by mutableStateOf(_players.first())

    // The player who shuffles and deals the cards
    var dealer by mutableStateOf(_players.first())

    // The next active player after the dealer
    val smallBlind by derivedStateOf { nextActivePlayerAfter(dealer, increment = 1) }

    // The next active player after the small blind player, 2nd after the dealer
    val bigBlind by derivedStateOf { nextActivePlayerAfter(dealer, increment = 2) }


    // = ACTIVE PLAYER MANAGEMENT
    fun nextActivePlayerAfter(
        player: Player,
        increment: Int = 1
    ): Player {
        return if (activePlayers.isNotEmpty()) {
            activePlayers[ ( activePlayers.indexOf(player) + increment) % activePlayers.size ]
        } else {
            _players.first() // Return null if no active players exist
        }
    }

    fun isActive(player: Player): Boolean {
        return player in activePlayers
    }

    fun isNotActive(player: Player): Boolean {
        return player !in activePlayers
    }


    // = FOCUS PLAYER MANAGEMENT
    fun setInitialFocusPlayer() {
        if (activePlayers.isNotEmpty()) {
            focus = nextActivePlayerAfter(dealer, increment = 3)
        } else {
            throw IndexOutOfBoundsException("NO ACTIVE PLAYERS: activeIDs IS EMPTY")
        }
    }

    fun incrementFocusPlayer() {
        if (activePlayers.isNotEmpty()) {
            focus = nextActivePlayerAfter(focus, increment = 1)
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


    // = STATUS MANAGEMENT
    fun updateStatusesOnBet(bettingPlayer: Player, isRaise: Boolean) {
        // Update statuses for other participating players to PARTIAL_MATCH
        if (isRaise) {
            _players.forEach { player ->
                if (player.status == PlayerStatus.BET_MATCHED) {
                    player.status = PlayerStatus.PARTIAL_MATCH
                }
            }
        }

        // Update status for betting player to be BET_MATCHED or RAISED
        bettingPlayer.status =
            if (isRaise) {
                PlayerStatus.RAISED
            } else {
                PlayerStatus.BET_MATCHED
            }
    }


    // = INITIATION
    fun setStartingBalances(startingChips: Int) {
        _players.forEach { player ->
            player.balance = startingChips
        }
    }

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