package com.sycarias.chipless.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.ui.theme.ChiplessShadowStyle
import com.sycarias.chipless.ui.theme.ChiplessTypography
import com.sycarias.chipless.ui.utils.TableDataViewModel

enum class GameStage {
    PREFLOP,
    FLOP,
    TURN,
    RIVER,
}

@Composable
fun GameTableScreen(navController: NavController, viewModel: TableDataViewModel) {
    // View Model Variables
    val activeDealerId by remember { viewModel.activeDealerId }
    val startingChips by remember { viewModel.startingChips }
    val bigBlind by remember { viewModel.bigBlind }
    val smallBlind by remember { viewModel.smallBlind }
    val playerNames = viewModel.playerNames
    val activePlayer = remember { viewModel.activePlayer }

    val gameStage: GameStage by remember { mutableStateOf(GameStage.PREFLOP) }
    val gameStageTitle = when(gameStage) {
        GameStage.PREFLOP -> "Pre-Flop"
        GameStage.FLOP -> "Flop"
        GameStage.TURN -> "Turn"
        GameStage.RIVER -> "River"
    }

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