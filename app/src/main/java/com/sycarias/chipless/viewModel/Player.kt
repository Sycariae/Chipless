package com.sycarias.chipless.viewModel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Player(
    isFocus: Boolean = false,
    isDealer: Boolean = false
) {
    var name: String by mutableStateOf("")
    var status: PlayerStatus by mutableStateOf(PlayerStatus.IDLE)
    var balance: Int by mutableIntStateOf(0)
    var currentBet: Int by mutableIntStateOf(0)

    // = FOCUS CHECK
    var isFocus: Boolean by mutableStateOf(isFocus)
    val isNotFocus: Boolean by derivedStateOf { !isFocus }

    // = DEALER CHECK
    var isDealer: Boolean by mutableStateOf(isDealer)
    val isNotDealer: Boolean by derivedStateOf { !isDealer }

    // = PARTICIPATING CHECK
    val isParticipating: Boolean by derivedStateOf {
        name.isNotBlank() && status !in listOf(
            PlayerStatus.SAT_OUT,
            PlayerStatus.ELIMINATED
        )
    }
    val isNotParticipating: Boolean by derivedStateOf { !isParticipating }

    // = ACTIVE CHECK
    val isActive: Boolean by derivedStateOf {
        name.isNotBlank() && status !in listOf(
            PlayerStatus.FOLDED,
            PlayerStatus.SAT_OUT,
            PlayerStatus.ALL_IN,
            PlayerStatus.ELIMINATED
        )
    }
    val isNotActive: Boolean by derivedStateOf { !isActive }

    // = BETTING CHECK
    val isBetting: Boolean by derivedStateOf { name.isNotBlank() && currentBet > 0 }
    val isNotBetting: Boolean by derivedStateOf { !isBetting }

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
