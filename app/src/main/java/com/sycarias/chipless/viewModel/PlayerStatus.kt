package com.sycarias.chipless.viewModel


/**
 * Represents the current status of a player in a betting round.
 *
 * This enum defines the various states a player can be in, during a betting round,
 * along with a corresponding visual indication for each state.
 */
enum class PlayerStatus {
    IDLE,           // Waiting on their turn                                                Indication: no icon and no grey out
    BET_MATCHED,    // Met the current bet                                                  Indication: green BET_MATCHED icon
    PARTIAL_MATCH,  // Made a bet but has not fully met the current bet                     Indication: orange PARTIAL_MATCH icon
    RAISED,         // Raised the current bet                                               Indication: green RAISED icon
    FOLDED,         // Cut their losses and out for the round                               Indication: grey out
    SAT_OUT,        // Sat the round out and will be automatically sat out next round too   Indication: grey out
    ALL_IN,         // Doesn't participate in betting for the rest of the round             Indication: green BET_MATCHED icon
}