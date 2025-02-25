package com.sycarias.chipless.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue

class Pot(
    balance: Int = 0,
    includedPlayerIDs: List<Int> = listOf(0,1,2,3,4,5,6,7,8,9)
) {
    private var balance by mutableIntStateOf(balance)
    val includedPlayerIDs = mutableStateListOf(*includedPlayerIDs.toTypedArray())

    fun deposit(amount: Int) {
        balance += amount
    }
    fun getBalance(): Int {
        return balance
    }
    fun reset() {
        balance = 0
    }
}
