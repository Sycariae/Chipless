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

    // = BALANCE AND BET MANAGEMENT
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
}
