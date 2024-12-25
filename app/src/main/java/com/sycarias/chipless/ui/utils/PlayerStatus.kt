package com.sycarias.chipless.ui.utils

// The different statuses that a player can have
enum class PlayerStatus {
    IDLE, // Waiting on their turn
    BET_MATCHED, // Met the current bet
    PARTIAL_MATCH, // Made a bet but has not fully met the current bet
    RAISED, // Raised the current bet
    FOLDED, // Cut their losses and out for the round
    SAT_OUT, // Sat the whole round out and won't be automatically added as an active player next round
}