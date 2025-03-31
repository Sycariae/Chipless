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
    var balance: Int by mutableIntStateOf(1)
    var currentBet: Int by mutableIntStateOf(0)


    // = FOCUS CHECK
    var isFocus: Boolean by mutableStateOf(isFocus)
    val isNotFocus: Boolean by derivedStateOf { !isFocus }


    // = DEALER CHECK
    var isDealer: Boolean by mutableStateOf(isDealer)
    val isNotDealer: Boolean by derivedStateOf { !isDealer }


    // = PARTICIPATING CHECK
    val isParticipating: Boolean by derivedStateOf {
        name.isNotBlank() && isNotEliminated && status !in listOf(
            PlayerStatus.SAT_OUT
        )
    }
    val isNotParticipating: Boolean by derivedStateOf { !isParticipating }


    // = ACTIVE CHECK
    val isActive: Boolean by derivedStateOf {
        name.isNotBlank() && isNotEliminated && status !in listOf(
            PlayerStatus.FOLDED,
            PlayerStatus.SAT_OUT,
            PlayerStatus.ALL_IN
        )
    }
    val isNotActive: Boolean by derivedStateOf { !isActive }


    // = BETTING CHECK
    val isBetting: Boolean by derivedStateOf { name.isNotBlank() && currentBet > 0 }
    val isNotBetting: Boolean by derivedStateOf { !isBetting }


    // = ELIMINATION CHECK
    val isEliminated: Boolean by derivedStateOf { balance == 0 && status != PlayerStatus.ALL_IN }
    val isNotEliminated: Boolean by derivedStateOf { !isEliminated }


    // = BALANCE AND BET MANAGEMENT
    fun pay(amount: Int) {
        balance += amount
    }

    fun bet(amount: Int) {
        currentBet += amount
        balance -= amount
    }


    // = STATUS MANAGEMENT
    fun fold() {
        status = PlayerStatus.FOLDED
    }


    // = RESET
    fun reset() {
        name = ""
        status = PlayerStatus.IDLE
        balance = 1
        currentBet = 0
    }
}
