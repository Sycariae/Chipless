package com.sycarias.chipless.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sycarias.chipless.GameTable
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.composables.IntInputField
import com.sycarias.chipless.ui.composables.PlayerTable
import com.sycarias.chipless.ui.composables.StaticShadow
import com.sycarias.chipless.ui.composables.TableScreen
import com.sycarias.chipless.ui.composables.presets.ActionButtonText
import com.sycarias.chipless.ui.composables.presets.Heading
import com.sycarias.chipless.ui.composables.presets.PrimaryActionButton
import com.sycarias.chipless.ui.extensions.buttonShadow
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.viewModel.ViewModel

enum class PlayerButtonLocation {
    TOP_ROW,
    MID_SECTION,
    BOTTOM_ROW
}

enum class PlayerButtonSide {
    LEFT,
    RIGHT
}

@Composable
fun CreateTableScreen(navController: NavController, viewModel: ViewModel) {
    // Define Sizing and Spacing for Dealer Icons
    val dealerIconSize = 30.dp // Sizing of Dealer Icons
    val dealerIconMidSpacing = 5.dp // Spacing Between Player Button and Dealer Icon in Mid-Section
    val dealerIconTBRHSpacing = (-6).dp // Horizontal Spacing Between Player Button and Dealer Icon in Top and Bottom Rows
    val dealerIconTBRVSpacing = (-10).dp // Vertical Spacing Between Player Button and Dealer Icon in Top and Bottom Rows

    // View Model Variables
    val tableConfig = viewModel.tableConfig
    val players = viewModel.players


    LaunchedEffect(Unit) {
        viewModel.resetForTableConfiguration()

        // TESTING START = TODO: REMOVE TESTING
        players.list.forEachIndexed { index, player ->
            player.name = "Player$index"
        }
        // TESTING END
    }


    // Input Field Validation
    val startingChipsValid by remember {
        derivedStateOf {
            tableConfig.startingChips in tableConfig.bigBlind * 10 .. 1000000
        }
    }
    val bigBlindValid by remember {
        derivedStateOf {
            tableConfig.bigBlind in tableConfig.smallBlind * 2..tableConfig.startingChips / 10
        }
    }
    val smallBlindValid by remember {
        derivedStateOf {
            tableConfig.smallBlind in 5..tableConfig.bigBlind / 2
        }
    }

    // Table Config Validation
    val tableConfigValid by remember {
        derivedStateOf {
            startingChipsValid
                && bigBlindValid
                && smallBlindValid
                && players.participatingList.count() >= 4
                && players.dealer.isActive
        }
    }

    // Define Theming of Play Button based on Table Config Validity
    val playButtonColor = when (tableConfigValid) {
        true -> ChiplessColors.primary
        false -> ChiplessColors.secondary
    }
    val playButtonModifier = when (tableConfigValid) {
        true -> Modifier
            .buttonShadow(
                color = ChiplessColors.primary,
                offsetX = 0.dp,
                offsetY = 0.dp,
                blurRadius = 20.dp,
                cornerRadius = 100.dp
            )
        false -> Modifier
    }
    val playButtonTextColor = when (tableConfigValid) {
        true -> ChiplessColors.textPrimary
        false -> ChiplessColors.textTertiary
    }

    /* = GOING TO MOVE ANY REQUIRED LOGIC TO OTHER FILES
    // Local Dealer Icon Composable using Identifiers
    @Composable
    fun CTSDealerIcon(
        player: Player,
        alpha: Float = 1f
    ) { // Create Table Screen Dealer Icon
        DealerIcon(
            active = (player == players.dealer),
            size = dealerIconSize,
            alpha = alpha,
            onClick = { players.setDealerPlayer(player) }
        )
    }

    // Local Player Button Composable using Identifiers
    @Composable
    fun CTSPlayerButton(
        player: Player,
        location: PlayerButtonLocation,
        side: PlayerButtonSide,
        onClick: () -> Unit = {}
    ) {
        fun checkForInversion(x: Dp): Dp {
            return if (side == PlayerButtonSide.RIGHT) { -x }
            else x
        }

        val offsetX: Dp = if (location == PlayerButtonLocation.MID_SECTION) {
            checkForInversion(dealerIconMidSpacing + dealerIconSize)
        } else {
            checkForInversion(dealerIconTBRHSpacing + dealerIconSize)
        }

        val offsetY: Dp = when (location) {
            PlayerButtonLocation.TOP_ROW ->
                dealerIconTBRVSpacing + dealerIconSize
            PlayerButtonLocation.MID_SECTION ->
                0.dp
            PlayerButtonLocation.BOTTOM_ROW ->
                -( dealerIconTBRVSpacing + dealerIconSize )
        }

        val dealerIconAlpha = animateFloatAsState(targetValue = if (players.isActive(player)) 1f else 0f, label = "Dealer Icon Fade In/Out")

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = when (side) {
                PlayerButtonSide.LEFT -> Arrangement.End
                PlayerButtonSide.RIGHT -> Arrangement.Start
            }
        ) {
            Box (
                contentAlignment = when (location) { // Determine Alignment based on positional args
                    PlayerButtonLocation.TOP_ROW ->
                        when (side) {
                            PlayerButtonSide.LEFT -> Alignment.BottomEnd
                            PlayerButtonSide.RIGHT -> Alignment.BottomStart
                        }
                    PlayerButtonLocation.MID_SECTION ->
                        when (side) {
                            PlayerButtonSide.LEFT -> Alignment.CenterEnd
                            PlayerButtonSide.RIGHT -> Alignment.CenterStart
                        }
                    PlayerButtonLocation.BOTTOM_ROW ->
                        when (side) {
                            PlayerButtonSide.LEFT -> Alignment.TopEnd
                            PlayerButtonSide.RIGHT -> Alignment.TopStart
                        }
                }
            ) {
                PlayerLabel(
                    player = player,
                    size = playerButtonSize,
                    screen = TableScreen.CREATE
                )
                if (dealerIconAlpha.value != 0f) {
                    Box(
                        modifier = Modifier
                            .offset(offsetX, offsetY)
                    ) {
                        CTSDealerIcon(
                            player = player,
                            alpha = dealerIconAlpha.value
                        )
                    }
                }
            }
        }
    }*/

    // START OF UI
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(35.dp))

        Heading(text = "Create Table")

        Spacer(Modifier.height(12.dp))

        // Settings Input Fields
        Row() {
            IntInputField(
                label = "Big Blind",
                initialValue = tableConfig.bigBlind.toString(),
                maxLen = 4,
                isValid = bigBlindValid,
                onValueChange = {
                    tableConfig.bigBlind = it.toInt()
                    tableConfig.smallBlind = ( it.toInt() / 2 )
                },
                modifier = Modifier.width(150.dp)
            )
            Spacer(Modifier.width(18.dp))

            IntInputField(
                label = "Small Blind",
                initialValue = tableConfig.smallBlind.toString(),
                isValid = smallBlindValid,
                maxLen = 4,
                onValueChange = { tableConfig.smallBlind = it.toInt() },
                modifier = Modifier.width(150.dp)
            )
        }
        Spacer(Modifier.height(12.dp))

        IntInputField(
            label = "Starting Chips",
            initialValue = tableConfig.startingChips.toString(),
            maxLen = 6,
            isValid = startingChipsValid,
            onValueChange = { tableConfig.startingChips = it.toInt() },
            modifier = Modifier.width(200.dp)
        )

        PlayerTable(
            players = players,
            screen = TableScreen.CREATE,
            modifier = Modifier.padding(bottom = 80.dp).fillMaxSize()
        )
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 25.dp, bottom = 25.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        PrimaryActionButton(
            onClick = {
                when (tableConfigValid) {
                    true -> { navController.navigate(GameTable) }
                    false -> {}
                }
            },
            modifier = playButtonModifier,
            color = playButtonColor
        ) {
            StaticShadow(
                blurRadius = 5.dp,
                color = Color.Black.copy(alpha = 0.4f),
                offsetX = (-3).dp,
                offsetY = 4.dp
            ) {
                Icon(
                    painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.icon_play)),
                    contentDescription = "Settings",
                    tint = playButtonTextColor,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            ActionButtonText(
                text = "Play",
                color = playButtonTextColor
            )
        }
    }
}