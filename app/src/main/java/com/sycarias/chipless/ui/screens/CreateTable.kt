package com.sycarias.chipless.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sycarias.chipless.GameTable
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.composables.DealerIcon
import com.sycarias.chipless.ui.composables.IntInputField
import com.sycarias.chipless.ui.composables.PlayerLabel
import com.sycarias.chipless.ui.composables.StaticShadow
import com.sycarias.chipless.ui.composables.presets.ActionButton
import com.sycarias.chipless.ui.composables.presets.ActionButtonText
import com.sycarias.chipless.ui.composables.presets.Heading
import com.sycarias.chipless.ui.extensions.buttonShadow
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.viewModel.Player
import com.sycarias.chipless.viewModel.TableDataViewModel

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
fun CreateTableScreen(navController: NavController, viewModel: TableDataViewModel) {
    // Define Sizing and Spacing for Dealer Icons
    val dealerIconSize = 30.dp // Sizing of Dealer Icons
    val dealerIconMidSpacing = 5.dp // Spacing Between Player Button and Dealer Icon in Mid-Section
    val dealerIconTBRHSpacing = (-6).dp // Horizontal Spacing Between Player Button and Dealer Icon in Top and Bottom Rows
    val dealerIconTBRVSpacing = (-10).dp // Vertical Spacing Between Player Button and Dealer Icon in Top and Bottom Rows

    // Define Sizing and Spacing for Player Buttons
    val playerButtonSize = 50.dp // Sizing of Player Buttons
    val playerButtonMidVSpacing = 32.dp // Player Button Vertical Spacing
    val playerButtonTBRVSpacing = playerButtonMidVSpacing /*- dealerIconTBRVSpacing*/ // Reduce spacing to accommodate for additional clearance
    val playerButtonMidHSpacing = 175.dp /*- (dealerIconMidClearance * 2)*/ // Player Button Middle Horizontal Spacing
    val playerButtonTBRHSpacing = 88.dp /*- (dealerIconTBRHClearance * 2)*/ // Player Button Top and Bottom Row Horizontal Spacing

    // View Model Variables
    val tableConfig = viewModel.tableConfig
    val players = viewModel.players

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
            (startingChipsValid
                    && bigBlindValid
                    && smallBlindValid
                    && players.participatingPlayers.count() >= 4)
                    && players.isActive(players.dealer)
        }
    }

    // Define Theming of Play Button based on Table Config Validity
    val playButtonColor = when (tableConfigValid) {
        true -> ChiplessColors.primary
        false -> ChiplessColors.secondary
    }
    val playButtonModifier = Modifier
        .height(55.dp)
        .then(
            when (tableConfigValid) {
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
        )
    val playButtonTextColor = when (tableConfigValid) {
        true -> ChiplessColors.textPrimary
        false -> ChiplessColors.textTertiary
    }

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
            onClick = { players.dealer = player }
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

        val offsetX: Dp = when {
            location == PlayerButtonLocation.MID_SECTION ->
                checkForInversion(
                    dealerIconMidSpacing + dealerIconSize
                )
            else ->
                checkForInversion(
                    dealerIconTBRHSpacing + dealerIconSize
                )
        }

        val offsetY: Dp = when (location) {
            PlayerButtonLocation.TOP_ROW ->
                dealerIconTBRVSpacing + dealerIconSize
            PlayerButtonLocation.MID_SECTION -> 0.dp
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
                    name = player.name,
                    size = playerButtonSize,
                    onClick = onClick
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
    }

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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.Center
        ) {
            // TABLE IMAGE
            StaticShadow(
                blurRadius = 120.dp,
                color = ChiplessColors.primary.copy(alpha = 0.2f)
            ) {
                StaticShadow(
                    blurRadius = 50.dp,
                    color = ChiplessColors.primary.copy(alpha = 0.3f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_table), // Cache Table Image
                        contentDescription = "Poker Table Image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(378.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row( // PLAYERS TOP ROW
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row (modifier = Modifier.weight(1f)) {
                        val player = players.list[0]
                        CTSPlayerButton(
                            player = player,
                            side = PlayerButtonSide.LEFT,
                            location = PlayerButtonLocation.TOP_ROW,
                            onClick = {
                                player.name = when {
                                    players.isNotActive(player) -> "Luke"
                                    else -> ""
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(playerButtonTBRHSpacing))

                    Row (modifier = Modifier.weight(1f)) {
                        val player = players.list[1]
                        CTSPlayerButton(
                            player = player,
                            side = PlayerButtonSide.RIGHT,
                            location = PlayerButtonLocation.TOP_ROW,
                            onClick = {
                                player.name = when {
                                    players.isNotActive(player) -> "Tallulah"
                                    else -> ""
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(playerButtonTBRVSpacing))

                Row( // PLAYER MIDDLE SECTION
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.End
                    ) { // LEFT
                        val player9 = players.list[9]
                        CTSPlayerButton(
                            player = player9,
                            side = PlayerButtonSide.LEFT,
                            location = PlayerButtonLocation.MID_SECTION,
                            onClick = {
                                player9.name = when {
                                    players.isNotActive(player9) -> "Hobo J"
                                    else -> ""
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(playerButtonMidVSpacing))

                        val player8 = players.list[8]
                        CTSPlayerButton(
                            player = player8,
                            side = PlayerButtonSide.LEFT,
                            location = PlayerButtonLocation.MID_SECTION,
                            onClick = {
                                player8.name = when {
                                    players.isNotActive(player8) -> "Kornrad"
                                    else -> ""
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(playerButtonMidVSpacing))

                        val player7 = players.list[7]
                        CTSPlayerButton(
                            player = player7,
                            side = PlayerButtonSide.LEFT,
                            location = PlayerButtonLocation.MID_SECTION,
                            onClick = {
                                player7.name = when {
                                    players.isNotActive(player7) -> "Nonrod"
                                    else -> ""
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(playerButtonMidHSpacing))

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) { // RIGHT
                        val player2 = players.list[2]
                        CTSPlayerButton(
                            player = player2,
                            side = PlayerButtonSide.RIGHT,
                            location = PlayerButtonLocation.MID_SECTION,
                            onClick = {
                                player2.name = when {
                                    players.isNotActive(player2) -> "Nef"
                                    else -> ""
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(playerButtonMidVSpacing))

                        val player3 = players.list[3]
                        CTSPlayerButton(
                            player = player3,
                            side = PlayerButtonSide.RIGHT,
                            location = PlayerButtonLocation.MID_SECTION,
                            onClick = {
                                player3.name = when {
                                    players.isNotActive(player3) -> "E-van"
                                    else -> ""
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(playerButtonMidVSpacing))

                        val player4 = players.list[4]
                        CTSPlayerButton(
                            player = player4,
                            side = PlayerButtonSide.RIGHT,
                            location = PlayerButtonLocation.MID_SECTION,
                            onClick = {
                                player4.name = when {
                                    players.isNotActive(player4) -> "Adam"
                                    else -> ""
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(playerButtonTBRVSpacing))

                Row( // PLAYERS BOTTOM ROW
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row (modifier = Modifier.weight(1f)) {
                        val player = players.list[6]
                        CTSPlayerButton(
                            player = player,
                            side = PlayerButtonSide.LEFT,
                            location = PlayerButtonLocation.BOTTOM_ROW,
                            onClick = {
                                player.name = when {
                                    players.isNotActive(player) -> "Bellamy"
                                    else -> ""
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(playerButtonTBRHSpacing))

                    Row (modifier = Modifier.weight(1f)) {
                        val player = players.list[5]
                        CTSPlayerButton(
                            player = player,
                            side = PlayerButtonSide.RIGHT,
                            location = PlayerButtonLocation.BOTTOM_ROW,
                            onClick = {
                                player.name = when {
                                    players.isNotActive(player) -> "Fred"
                                    else -> ""
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 25.dp, bottom = 25.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        ActionButton(
            onClick = {
                when (tableConfigValid) {
                    true -> {
                        viewModel.initialiseNewTable()
                        navController.navigate(GameTable)
                    }
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