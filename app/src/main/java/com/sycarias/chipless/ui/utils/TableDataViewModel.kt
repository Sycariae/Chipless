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

    // PLAYERS
    private val _playerNames = mutableStateListOf(*Array(10) { "" })
    val playerNames: SnapshotStateList<String> = _playerNames
    val activePlayer by derivedStateOf { playerNames[activeDealerId.value] } // TODO: Make updatable based on game stage.

    // Update a specific player's name
    fun updatePlayerName(id: Int, name: String) {
        _playerNames[id] = name
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