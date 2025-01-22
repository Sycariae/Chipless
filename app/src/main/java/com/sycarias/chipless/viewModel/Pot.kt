package com.sycarias.chipless.viewModel

data class Pot(
    private var balance: Int = 0,
    val includedPlayerIDs: List<Int> = listOf(0,1,2,3,4,5,6,7,8,9)
) {
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
