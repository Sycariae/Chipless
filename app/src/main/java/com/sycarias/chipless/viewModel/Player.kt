package com.sycarias.chipless.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Player {
    var name: String by mutableStateOf("")
    var status: PlayerStatus by mutableStateOf(PlayerStatus.IDLE)
    var balance: Int by mutableIntStateOf(0)
    var currentBet: Int by mutableIntStateOf(0)
        private set

    fun pay(amount: Int) {
        balance += amount
    }

    fun bet(amount: Int) {
        currentBet += amount
        balance -= amount
    }

    // = ELIMINATION
    fun eliminate() {
        status = PlayerStatus.ELIMINATED
    }

    // = RESETS
    fun resetForNewRound() {
        currentBet = 0
        if (status !in listOf(PlayerStatus.SAT_OUT, PlayerStatus.FOLDED, PlayerStatus.ALL_IN)) {
            status = PlayerStatus.IDLE
        }
    }
    fun resetForNewMatch() {
        currentBet = 0
        if (status != PlayerStatus.SAT_OUT) {
            status = PlayerStatus.IDLE
        }
    }
    fun resetForNewTable() {
        currentBet = 0
        status = PlayerStatus.IDLE
    }
}
