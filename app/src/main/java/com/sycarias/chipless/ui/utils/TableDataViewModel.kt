package com.sycarias.chipless.ui.utils

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class TableDataViewModel: ViewModel() {
    // DEALER SELECTION
    private val _activeDealerId = mutableIntStateOf(0)
    val activeDealerId: State<Int> = _activeDealerId

    // Update the active dealer
    fun setActiveDealer(id: Int) {
        _activeDealerId.intValue = id
    }

    // PLAYER LIST
    private val _playerNames = mutableStateListOf(*Array(10) { "" })
    val playerNames: SnapshotStateList<String> = _playerNames
    val playerCount = derivedStateOf { _playerNames.count { it.isNotBlank() } }

    // Update a specific player's name
    fun updatePlayerName(id: Int, name: String) {
        _playerNames[id] = name
    }

    // ACTIVE PLAYER
    private val _activePlayer = mutableIntStateOf(activeDealerId.value)
    val activePlayer: State<Int> = _activePlayer
    val activePlayerName = derivedStateOf { playerNames[activePlayer.value] }

    fun updateActivePlayer(id: Int) {
        _activePlayer.intValue = id
    }

    // GAME STAGE
    private val _gameStage = mutableStateOf(GameStage.PREFLOP)
    val gameStage: State<GameStage> = _gameStage

    // Update game stage to next stage
    fun incrementGameStage() {
        _gameStage.value = when(_gameStage.value) {
            GameStage.PREFLOP -> GameStage.FLOP
            GameStage.FLOP -> GameStage.TURN
            GameStage.TURN -> GameStage.RIVER
            GameStage.RIVER -> GameStage.SHOWDOWN
            GameStage.SHOWDOWN -> GameStage.PREFLOP
        }
    }

    // TABLE CONFIG
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
}