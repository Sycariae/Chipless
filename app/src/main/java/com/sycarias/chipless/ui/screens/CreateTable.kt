package com.sycarias.chipless.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
    val startingChips by remember { viewModel.startingChips }
    val bigBlind by remember { viewModel.bigBlind }
    val smallBlind by remember { viewModel.smallBlind }
    val players = viewModel.players

    // Input Field Validation
    val startingChipsValid by remember {
        derivedStateOf {
            startingChips in bigBlind * 10 .. 1000000
        }
    }
    val bigBlindValid by remember {
        derivedStateOf {
            bigBlind in smallBlind * 2..startingChips / 10
        }
    }
    val smallBlindValid by remember {
        derivedStateOf {
            smallBlind in 5..bigBlind / 2
        }
    }

    // Table Config Validation
    val tableConfigValid by remember {
        derivedStateOf {
            (startingChipsValid
                    && bigBlindValid
                    && smallBlindValid
                    && players.participatingIDs.count() >= 4)
                    && players.isActive(players.dealerID)
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
        playerID:Int,
        alpha:Float = 1f
    ) { // Create Table Screen Dealer Icon
        DealerIcon(
            active = (playerID == players.dealerID),
            size = dealerIconSize,
            alpha = alpha,
            onClick = { players.setDealerPlayer(playerID) }
        )
    }

    // Local Player Button Composable using Identifiers
    @Composable
    fun CTSPlayerButton(
        playerID: Int,
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

        val dealerIconAlpha = animateFloatAsState(targetValue = if (players.isActive(playerID)) 1f else 0f, label = "Dealer Icon Fade In/Out")

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
                    name = players.getPlayerName(playerID).value,
                    size = playerButtonSize,
                    onClick = onClick
                )
                if (dealerIconAlpha.value != 0f) {
                    Box(
                        modifier = Modifier
                            .offset(offsetX, offsetY)
                    ) {
                        CTSDealerIcon(
                            playerID = playerID,
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
                initialValue = bigBlind.toString(),
                maxLen = 4,
                isValid = bigBlindValid,
                onValueChange = {
                    viewModel.setBigBlind( it.toInt() )
                    viewModel.setSmallBlind( it.toInt() / 2 )
                },
                modifier = Modifier.width(150.dp)
            )
            Spacer(Modifier.width(18.dp))

            IntInputField(
                label = "Small Blind",
                initialValue = smallBlind.toString(),
                isValid = smallBlindValid,
                maxLen = 4,
                onValueChange = { viewModel.setSmallBlind(it.toInt()) },
                modifier = Modifier.width(150.dp)
            )
        }
        Spacer(Modifier.height(12.dp))

        IntInputField(
            label = "Starting Chips",
            initialValue = startingChips.toString(),
            maxLen = 6,
            isValid = startingChipsValid,
            onValueChange = { viewModel.setStartingChips(it.toInt()) },
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
                        CTSPlayerButton(
                            playerID = 0,
                            side = PlayerButtonSide.LEFT,
                            location = PlayerButtonLocation.TOP_ROW,
                            onClick = {
                                players.setPlayerName(
                                    playerID = 0,
                                    newName = when {
                                        players.isNotActive(0) -> "Luke"
                                        else -> ""
                                    }
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(playerButtonTBRHSpacing))

                    Row (modifier = Modifier.weight(1f)) {
                        CTSPlayerButton(
                            playerID = 1,
                            side = PlayerButtonSide.RIGHT,
                            location = PlayerButtonLocation.TOP_ROW,
                            onClick = {
                                players.setPlayerName(
                                    playerID = 1,
                                    newName = when {
                                        players.isNotActive(1) -> "Tallulah"
                                        else -> ""
                                    }
                                )
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
                        CTSPlayerButton(
                            playerID = 9,
                            side = PlayerButtonSide.LEFT,
                            location = PlayerButtonLocation.MID_SECTION,
                            onClick = {
                                players.setPlayerName(
                                    playerID = 9,
                                    newName = when {
                                        players.isNotActive(9) -> "Hobo J."
                                        else -> ""
                                    }
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(playerButtonMidVSpacing))

                        CTSPlayerButton(
                            playerID = 8,
                            side = PlayerButtonSide.LEFT,
                            location = PlayerButtonLocation.MID_SECTION,
                            onClick = {
                                players.setPlayerName(
                                    playerID = 8,
                                    newName = when {
                                        players.isNotActive(8) -> "Kornrad"
                                        else -> ""
                                    }
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(playerButtonMidVSpacing))

                        CTSPlayerButton(
                            playerID = 7,
                            side = PlayerButtonSide.LEFT,
                            location = PlayerButtonLocation.MID_SECTION,
                            onClick = {
                                players.setPlayerName(
                                    playerID = 7,
                                    newName = when {
                                        players.isNotActive(7) -> "Nonrod"
                                        else -> ""
                                    }
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(playerButtonMidHSpacing))

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) { // RIGHT
                        CTSPlayerButton(
                            playerID = 2,
                            side = PlayerButtonSide.RIGHT,
                            location = PlayerButtonLocation.MID_SECTION,
                            onClick = {
                                players.setPlayerName(
                                    playerID = 2,
                                    newName = when {
                                        players.isNotActive(2) -> "Nef"
                                        else -> ""
                                    }
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(playerButtonMidVSpacing))

                        CTSPlayerButton(
                            playerID = 3,
                            side = PlayerButtonSide.RIGHT,
                            location = PlayerButtonLocation.MID_SECTION,
                            onClick = {
                                players.setPlayerName(
                                    playerID = 3,
                                    newName = when {
                                        players.isNotActive(3) -> "E-van"
                                        else -> ""
                                    }
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(playerButtonMidVSpacing))

                        CTSPlayerButton(
                            playerID = 4,
                            side = PlayerButtonSide.RIGHT,
                            location = PlayerButtonLocation.MID_SECTION,
                            onClick = {
                                players.setPlayerName(
                                    playerID = 4,
                                    newName = when {
                                        players.isNotActive(4) -> "Adam"
                                        else -> ""
                                    }
                                )
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
                        CTSPlayerButton(
                            playerID = 6,
                            side = PlayerButtonSide.LEFT,
                            location = PlayerButtonLocation.BOTTOM_ROW,
                            onClick = {
                                players.setPlayerName(
                                    playerID = 6,
                                    newName = when {
                                        players.isNotActive(6) -> "Bellamy"
                                        else -> ""
                                    }
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(playerButtonTBRHSpacing))

                    Row (modifier = Modifier.weight(1f)) {
                        CTSPlayerButton(
                            playerID = 5,
                            side = PlayerButtonSide.RIGHT,
                            location = PlayerButtonLocation.BOTTOM_ROW,
                            onClick = {
                                players.setPlayerName(
                                    playerID = 5,
                                    newName = when {
                                        players.isNotActive(5) -> "Fred"
                                        else -> ""
                                    }
                                )
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