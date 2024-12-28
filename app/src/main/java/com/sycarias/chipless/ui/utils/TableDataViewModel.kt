package com.sycarias.chipless.ui.utils

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class TableDataViewModel: ViewModel() {
    // = TABLE CONFIG
    private val _startingChips = mutableIntStateOf(1000)
    val startingChips: State<Int> = _startingChips

    private val _bigBlind = mutableIntStateOf(10)
    val bigBlind: State<Int> = _bigBlind

    private val _smallBlind = mutableIntStateOf(5)
    val smallBlind: State<Int> = _smallBlind

    fun setStartingChips(value: Int) {
        _startingChips.intValue = value
    }

    fun setBigBlind(value: Int) {
        _bigBlind.intValue = value
    }

    fun setSmallBlind(value: Int) {
        _smallBlind.intValue = value
    }


    // = DEALER SELECTION
    private val _activeDealerId: MutableState<Int?> = mutableStateOf(null)
    val activeDealerId: State<Int?> = _activeDealerId

    // Update the active dealer
    fun setActiveDealer(playerID: Int) {
        _activeDealerId.value = playerID
    }


    // = BETS AND BALANCES
    private val _playerBalances = mutableStateListOf(*Array(10) { _startingChips.intValue })
    val playerBalances: SnapshotStateList<Int> = _playerBalances

    private val _playerCurrentBets = mutableStateListOf(*Array(10) { 0 })
    val playerCurrentBets: SnapshotStateList<Int> = _playerCurrentBets

    private val _currentTableBet = derivedStateOf { _playerCurrentBets.maxOrNull() ?: 0}
    val currentTableBet: State<Int> = _currentTableBet

    private val _tablePot = mutableIntStateOf(0)
    val tablePot: State<Int> = _tablePot

    fun callBet(playerID: Int) {
        placeBet(playerID, betAmount = _currentTableBet.value)
    }

    fun placeBet(playerID: Int, betAmount: Int, isRaise: Boolean = false) {
        // Add bet amount to table pot and set table bet
        _tablePot.intValue += betAmount

        // Update bet amount and balance for betting player
        _playerCurrentBets[playerID] += betAmount
        _playerBalances[playerID] -= betAmount

        updateStatusesOnAction(playerID, isRaise = isRaise)
    }


    // = PLAYER NAMES
    private val _playerNames = mutableStateListOf(*Array(10) { "" })
    val playerNames: SnapshotStateList<String> = _playerNames
    val playerCount = derivedStateOf { _playerNames.count { it.isNotBlank() } }

    // Update a specific player's name
    fun updatePlayerName(playerID: Int, name: String) {
        _playerNames[playerID] = name
    }


    // = PLAYER STATUSES
    private val _playerStatuses = mutableStateListOf(*Array(10) { PlayerStatus.IDLE })
    val playerStatuses: SnapshotStateList<PlayerStatus> = _playerStatuses

    // Update a specific player's status
    fun updatePlayerStatus(playerID: Int, status: PlayerStatus) {
        _playerStatuses[playerID] = status
    }

    // Update all statuses when someone raises
    private fun updateStatusesOnAction(actionPlayerID: Int, isRaise: Boolean = false) {
        _playerStatuses[actionPlayerID] = if (isRaise) PlayerStatus.RAISED else PlayerStatus.BET_MATCHED

        // Adjust statuses for other participating players
        _playerStatuses.forEachIndexed { playerID, status ->
            if (status == PlayerStatus.BET_MATCHED) {
                _playerStatuses[playerID] = PlayerStatus.PARTIAL_MATCH
            }
        }
    }


    // = PARTICIPATING PLAYERS
    private val _participatingPlayers by derivedStateOf {
        _playerNames.indices.filter { playerID ->
            _playerNames[playerID].isNotBlank() && _playerStatuses[playerID] !in listOf(PlayerStatus.FOLDED, PlayerStatus.SAT_OUT)
        }
    }


    // = ACTIVE PLAYER
    private val _activePlayer = mutableIntStateOf(activeDealerId.value ?: _participatingPlayers.first())
    val activePlayer: State<Int?> = _activePlayer

    // Update the active player id
    fun updateActivePlayer(playerID: Int) {
        _activePlayer.intValue = playerID
    }

    // Increment to the next active player (filtering out empty and inactive players)
    fun incrementActivePlayer() {
        val nextIndex = _participatingPlayers.indexOfFirst { it > _activePlayer.intValue }
        _activePlayer.intValue = if (nextIndex != -1) _participatingPlayers[nextIndex] else _participatingPlayers.firstOrNull() ?: 0
    }


    // = GAME STAGE
    private val _bettingRound = mutableStateOf(BettingRound.PREFLOP)
    val bettingRound: State<BettingRound> = _bettingRound

    // Update game stage to next stage
    private fun incrementGameStage() {
        _bettingRound.value = when(_bettingRound.value) {
            BettingRound.PREFLOP -> BettingRound.FLOP
            BettingRound.FLOP -> BettingRound.TURN
            BettingRound.TURN -> BettingRound.RIVER
            BettingRound.RIVER -> BettingRound.SHOWDOWN
            BettingRound.SHOWDOWN -> BettingRound.PREFLOP
        }
    }


    // = RESETS
    fun initiateNewRound() {
        // Reset player statuses excluding FOLDED, ALL_IN and SAT_OUT
        _playerStatuses.replaceAll { status ->
            when(status) {
                PlayerStatus.FOLDED -> PlayerStatus.FOLDED
                PlayerStatus.ALL_IN -> PlayerStatus.ALL_IN
                PlayerStatus.SAT_OUT -> PlayerStatus.SAT_OUT
                else -> PlayerStatus.IDLE
            }
        }
        _playerCurrentBets.fill(0) // Reset all current player bets

        incrementGameStage() // Set game stage to next stage from the current one
    }

    fun initiateNewMatch() {
        // Reset player statuses excluding SAT_OUT
        _playerStatuses.replaceAll { status ->
            if (status != PlayerStatus.SAT_OUT) PlayerStatus.IDLE else PlayerStatus.SAT_OUT
        }
        _playerCurrentBets.fill(0) // Reset all current player bets

        _bettingRound.value = BettingRound.PREFLOP // Set game stage to first stage: PREFLOP
    }
}