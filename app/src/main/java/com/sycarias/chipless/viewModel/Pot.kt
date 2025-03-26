package com.sycarias.chipless.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue

class Pot(
    balance: Int = 0,
    includedPlayers: List<Player> = listOf()
) {
    var balance by mutableIntStateOf(balance)
        private set
    val includedPlayers = mutableStateListOf(*includedPlayers.toTypedArray())

    fun deposit(amount: Int) {
        balance += amount
    }
    fun reset() {
        balance = 0
    }
}
