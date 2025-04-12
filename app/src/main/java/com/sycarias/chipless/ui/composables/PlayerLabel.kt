package com.sycarias.chipless.ui.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.extensions.dropShadow
import com.sycarias.chipless.ui.extensions.innerShadow
import com.sycarias.chipless.ui.theme.ChiplessButtonColors
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.ui.theme.ChiplessTypography
import com.sycarias.chipless.ui.utils.GlowIntensity
import com.sycarias.chipless.ui.utils.measureTextWidth
import com.sycarias.chipless.viewModel.Player
import com.sycarias.chipless.viewModel.PlayerStatus

enum class PlayerLabelLocation {
    TOP,
    MID,
    BOTTOM
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerLabel(
    player: Player,
    size: Dp = 50.dp,
    screen: TableScreen
) {
    // = NEW PLAYER DIALOG
    var showDialog by remember { mutableStateOf(false) }
    var dialogInput by remember { mutableStateOf("") }
    val isDialogInputValid by remember {
        derivedStateOf {
            !(player.name.isEmpty() && dialogInput.isEmpty()) // When trying to set player name from nothing to nothing TODO: Add any other validation checks
        }
    }
    val dialogTitle by remember {
        derivedStateOf {
            if (player.name.isEmpty())
                "Add Player"
            else
                "Edit Player"
        }
    }
    val dialogButtonText by remember {
        derivedStateOf {
            if (player.name.isNotEmpty()) // If renaming or removing a player
                if (dialogInput.isEmpty()) "Remove"
                else "Rename"
            else // If adding a player or input is invalid as you can't set player name from nothing to nothing)
                "Confirm"
        }
    }

    // = LABEL CONFIG
    val showChips: Boolean = remember { screen == TableScreen.GAME }
    val hideOnEmpty: Boolean = showChips
    val glowIntensity: GlowIntensity by remember {
        derivedStateOf {
            if (screen == TableScreen.CREATE) {
                GlowIntensity.HIGH
            } else {
                if (player.isFocus) GlowIntensity.HIGH else GlowIntensity.LOW
            }
        }
    }
    val greyedOut: Boolean by remember {
        derivedStateOf {
            if (screen == TableScreen.CREATE) {
                false
            } else {
                player.isEliminated || player.status in listOf(
                    PlayerStatus.FOLDED,
                    PlayerStatus.SAT_OUT
                )
            }
        }
    }
    val onClick: () -> Unit =
        if (screen == TableScreen.CREATE) {
            {
                dialogInput = ""
                showDialog = true
            }
        } else {
            {
                if (player.isEliminated) {
                    player.reset() // TODO: Make sure that the focus, next focus and dealer is accounted for
                }
            }
        }

    // = NAME DISPLAY
    val nameTextStyle = ChiplessTypography.body
    val nameTextColor by remember { derivedStateOf { if (greyedOut) ChiplessColors.textTertiary else ChiplessColors.textPrimary } }
    val nameTextPadding = 16.dp
    val nameDisplayWidth = measureTextWidth(text = player.name, style = nameTextStyle) + (nameTextPadding * 2)

    // = CHIPS DISPLAY
    val chipsTextStyle = ChiplessTypography.l3
    val chipsIconSize = 14.dp
    val chipsDisplayPadding = 20.dp
    val chipsDisplayWidth = chipsIconSize + measureTextWidth(text = player.balance.toString(), style = chipsTextStyle) + (chipsDisplayPadding * 2)

    // = LABEL SIZING
    val cornerRadius = 100.dp
    val shape = RoundedCornerShape(cornerRadius)
    val width = when {
        player.name.isEmpty() -> size // When Empty, default to this
        else -> max(nameDisplayWidth, chipsDisplayWidth)
    }
    val animatedWidth = animateDpAsState(targetValue = width, label = "Player Button Width")

    // = LABEL COLOURING
    val glowColor = ChiplessColors.primary
    val borderColor =
        if (!greyedOut) {
            when (glowIntensity) {
                GlowIntensity.HIGH -> glowColor
                GlowIntensity.LOW -> glowColor.copy(alpha = 0.1f)
            }
        } else Color.Transparent

    // = UI START
    if (showDialog) {
        InputDialog(
            title = remember { dialogTitle },
            confirmText = dialogButtonText,
            isConfirmEnabled = (isDialogInputValid),
            onDismiss = { showDialog = false },
            onConfirm = {
                player.name = dialogInput
                showDialog = false
            }
        ) {
            InputField(
                onValueChange = { dialogInput = it },
                maxLen = 6,
                label = "Player Name"
            )
        }
    }
    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        if (!(player.name.isEmpty() && hideOnEmpty)) {
            Button(
                onClick = onClick,
                modifier = Modifier
                    .height(size)
                    .width(animatedWidth.value)
                    .border(
                        width = (0.5).dp,
                        shape = shape,
                        color = borderColor
                    )
                    .innerShadow(
                        shape = shape,
                        color = glowColor.copy(alpha = glowIntensity.inner.glow),
                        blur = glowIntensity.inner.blur,
                        offsetX = 0.dp,
                        offsetY = 0.dp
                    )
                    .innerShadow(
                        shape = shape,
                        color = glowColor.copy(
                            alpha = (glowIntensity.inner.glow * 2).coerceAtMost(
                                1f
                            )
                        ),
                        blur = (glowIntensity.inner.blur - 10.dp).coerceAtLeast(0.dp),
                        offsetX = 0.dp,
                        offsetY = 0.dp
                    )
                    .dropShadow(
                        color = glowColor.copy(alpha = glowIntensity.outer.glow),
                        blurRadius = glowIntensity.inner.blur,
                        cornerRadius = cornerRadius
                    )
                    .dropShadow(
                        color = glowColor.copy(
                            alpha = (glowIntensity.outer.glow * 2).coerceAtMost(
                                1f
                            )
                        ),
                        blurRadius = (glowIntensity.outer.blur - 20.dp).coerceAtLeast(0.dp),
                        cornerRadius = cornerRadius
                    ),
                shape = CircleShape,
                colors = ChiplessButtonColors(if (greyedOut) ChiplessColors.greyOut else ChiplessColors.secondary),
                contentPadding = PaddingValues(0.dp)
            ) {
                // TODO: ADD DEALER SELECTION BUTTONS IN CREATE TABLE SCREEN
                // TODO: ADD DEALER ICON DISPLAYS IN GAME SCREEN
                // TODO: ADD STATUS ICON DISPLAYS IN GAME SCREEN
                if (player.name.isNotEmpty()) {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = player.name,
                            style = nameTextStyle,
                            color = nameTextColor,
                            softWrap = false
                        )
                        if (showChips) {
                            Row (
                                modifier = Modifier.padding(bottom = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.image_chip),
                                    contentDescription = "Chips Icon",
                                    modifier = Modifier
                                        .size(chipsIconSize)
                                        .dropShadow(
                                            color = ChiplessColors.primary,
                                            blurRadius = 5.dp,
                                            cornerRadius = 100.dp
                                        )
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = player.balance.toString(),
                                    style = chipsTextStyle,
                                    color = nameTextColor,
                                    softWrap = false
                                )
                            }
                        }
                    }
                } else {
                    Icon(
                        painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.icon_add)),
                        contentDescription = "Add Icon",
                        modifier = Modifier
                            .size(17.dp)
                    )
                }
            }
        }
    }
}