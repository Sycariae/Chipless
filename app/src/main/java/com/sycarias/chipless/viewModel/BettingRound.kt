package com.sycarias.chipless.viewModel


/**
 * Represents the different rounds of betting in a poker game.
 *
 * Each round signifies a different stage of the game where players can make bets:
 * 1. PREFLOP - The initial betting round before any community cards are dealt.
 * 2. FLOP - The second betting round, after three community cards (the flop) are dealt.
 * 3. TURN - The third betting round, after a fourth community card (the turn) is dealt.
 * 4. RIVER - The fourth betting round, after the fifth and final community card (the river) is dealt.
 * 5. SHOWDOWN - The final stage, where remaining players reveal their cards to determine the winner.
 */
enum class BettingRound {
    PREFLOP,
    FLOP,
    TURN,
    RIVER,
    SHOWDOWN
}