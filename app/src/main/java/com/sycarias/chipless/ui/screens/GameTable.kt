package com.sycarias.chipless.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.ui.theme.ChiplessShadowStyle
import com.sycarias.chipless.ui.theme.ChiplessTypography

enum class GameStage {
    PREFLOP,
    FLOP,
    TURN,
    RIVER,
}

@Composable
fun GameTableScreen(navController: NavController/*, activeDealerId: State<Int?>*/) {
    var gameStage: GameStage by remember { mutableStateOf(GameStage.PREFLOP) }
    val gameStageTitle = when(gameStage) {
        GameStage.PREFLOP -> "Pre-Flop"
        GameStage.FLOP -> "Flop"
        GameStage.TURN -> "Turn"
        GameStage.RIVER -> "River"
    }
    var activePlayer by remember { mutableStateOf("Luke") }

    // START OF UI
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(45.dp))

        // Game Stage Title
        Text(
            text = gameStageTitle,
            style = ChiplessShadowStyle(
                style = ChiplessTypography.h1,
                offsetX = -12f,
                offsetY = 12f,
                blurRadius = 25f
            ),
            color = ChiplessColors.textPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))

        // Player Turn Subtitle
        Text(
            text = "$activePlayer's Turn",
            style = ChiplessShadowStyle(
                style = ChiplessTypography.h2,
                offsetX = -12f,
                offsetY = 12f,
                blurRadius = 25f
            ),
            color = ChiplessColors.textSecondary
        )
    }
}