package com.sycarias.chipless.viewModel

data class Player(
    var name: String = "",
    var status: PlayerStatus = PlayerStatus.IDLE,
    var balance: Int = 0,
    var currentBet: Int = 0
) {
    fun setName(newName: String) {
        name = newName
    }

    fun setStatus(newStatus: PlayerStatus) {
        status = newStatus
    }

    fun setBalance(amount: Int) {
        balance = amount
    }

    fun pay(amount: Int) {
        balance += amount
    }

    fun bet(amount: Int) {
        currentBet += amount
        balance -= amount
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
