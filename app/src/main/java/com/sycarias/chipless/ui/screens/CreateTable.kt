package com.sycarias.chipless.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.composables.AddPlayerButton
import com.sycarias.chipless.ui.composables.IntInputField
import com.sycarias.chipless.ui.composables.Shadowed
import com.sycarias.chipless.ui.extensions.buttonShadow
import com.sycarias.chipless.ui.theme.ChiplessButtonColors
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.ui.theme.ChiplessShadowStyle
import com.sycarias.chipless.ui.theme.ChiplessTypography

@Composable
fun CreateTable(navController: NavController) {
    // Default Values for Text Fields
    var startingChips by remember { mutableStateOf("1000") }
    var bigBlind by remember { mutableStateOf("10") }
    var smallBlind by remember { mutableStateOf("5") }

    val startingChipsValid by remember {
        derivedStateOf { (startingChips.toIntOrNull() ?: 0) >= (bigBlind.toIntOrNull() ?: 0) * 10 }
    }
    val bigBlindValid by remember {
        derivedStateOf { (bigBlind.toIntOrNull() ?: 0) in (smallBlind.toIntOrNull() ?: 0)*2 .. (startingChips.toIntOrNull() ?: Int.MAX_VALUE) / 10 }
    }
    val smallBlindValid by remember {
        derivedStateOf {
            (smallBlind.toIntOrNull() ?: 0) <= (bigBlind.toIntOrNull() ?: Int.MAX_VALUE) / 2
        }
    }

    // Add Player Button Spacing
    val aPBVSpacing = 32.dp // Vertical Spacing
    val aPBRowHSpacing = 88.dp // Top and Bottom Row Horizontal Spacing
    val aPBMidHSpacing = 175.dp // Middle Horizontal Spacing

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Title
        Text(
            modifier = Modifier.padding(top = 45.dp),
            text = "Create Table",
            style = ChiplessShadowStyle(
                style = ChiplessTypography.h1,
                offsetX = -12f,
                offsetY = 12f,
                blurRadius = 25f
            ),
            color = ChiplessColors.textPrimary
        )

        // Settings Input Fields
        IntInputField(
            label = "Starting Chips",
            initialValue = startingChips,
            maxLen = 6,
            isValid = startingChipsValid,
            onValueChange = { startingChips = it },
            modifier = Modifier
                .width(240.dp)
                .padding(bottom = 15.dp, top = 20.dp)
        )

        Row() {
            IntInputField(
                label = "Big Blind",
                initialValue = bigBlind,
                maxLen = 4,
                isValid = bigBlindValid,
                onValueChange = { bigBlind = it },
                modifier = Modifier
                    .width(160.dp)
                    .padding(end = 20.dp)
            )
            IntInputField(
                label = "Small Blind",
                initialValue = smallBlind,
                isValid = smallBlindValid,
                maxLen = 4,
                onValueChange = { smallBlind = it },
                modifier = Modifier.width(160.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.Center
        ) {
            // TABLE IMAGE
            Shadowed(
                blurRadius = 120.dp,
                color = ChiplessColors.primary.copy(alpha = 0.2f)
            ) {
                Shadowed(
                    blurRadius = 50.dp,
                    color = ChiplessColors.primary.copy(alpha = 0.25f)
                ) {
                    val tableImage = painterResource(id = R.drawable.image_table) // Add Table Image in Drawable
                    Image(
                        painter = remember { tableImage }, // Cache Table Image
                        contentDescription = "Poker Table Image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(60.dp)
                            .width(225.dp)
                            .height(378.dp)
                    )
                }
            }

            Shadowed(
                blurRadius = 35.dp,
                color = ChiplessColors.primary.copy(alpha = 0.2f)
            ) {
                Shadowed(
                    blurRadius = 5.dp,
                    color = ChiplessColors.primary.copy(alpha = 0.5f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row( // PLAYERS TOP ROW
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            AddPlayerButton { /*TODO APB*/ }
                            Spacer(modifier = Modifier.width(aPBRowHSpacing))
                            AddPlayerButton { /*TODO APB*/ }
                        }
                        Spacer(modifier = Modifier.height(aPBVSpacing))
                        Row( // PLAYER MIDDLE SECTION
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column() { // LEFT
                                AddPlayerButton { /*TODO APB*/ }
                                Spacer(modifier = Modifier.height(aPBVSpacing))
                                AddPlayerButton { /*TODO APB*/ }
                                Spacer(modifier = Modifier.height(aPBVSpacing))
                                AddPlayerButton { /*TODO APB*/ }
                            }
                            Spacer(modifier = Modifier.width(aPBMidHSpacing))
                            Column() { // RIGHT
                                AddPlayerButton { /*TODO APB*/ }
                                Spacer(modifier = Modifier.height(aPBVSpacing))
                                AddPlayerButton { /*TODO APB*/ }
                                Spacer(modifier = Modifier.height(aPBVSpacing))
                                AddPlayerButton { /*TODO APB*/ }
                            }
                        }
                        Spacer(modifier = Modifier.height(aPBVSpacing))
                        Row( // PLAYERS BOTTOM ROW
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            AddPlayerButton { /*TODO APB*/ }
                            Spacer(modifier = Modifier.width(aPBRowHSpacing))
                            AddPlayerButton { /*TODO APB*/ }
                        }
                    }
                }
            }
        }
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 25.dp, bottom = 25.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { /* TODO: Menu Button Click */ },
                modifier = Modifier
                    .width(135.dp)
                    .height(55.dp)
                    .buttonShadow(
                        color = ChiplessColors.primary,
                        offsetX = 0.dp,
                        offsetY = 0.dp,
                        blurRadius = 20.dp,
                        cornerRadius = 100.dp
                    ),
                shape = RoundedCornerShape(100.dp),
                colors = ChiplessButtonColors(ChiplessColors.primary)
            ) {
                Shadowed(
                    blurRadius = 5.dp,
                    color = Color.Black.copy(alpha = 0.4f),
                    offsetX = (-3).dp,
                    offsetY = 4.dp
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_play),
                        contentDescription = "Settings",
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Play",
                    color = ChiplessColors.textPrimary,
                    style = ChiplessShadowStyle(style = ChiplessTypography.sh2)
                )
            }
        }
    }
}