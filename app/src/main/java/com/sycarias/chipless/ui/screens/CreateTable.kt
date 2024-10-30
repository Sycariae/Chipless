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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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

@Suppress("UNUSED_PARAMETER")
@Composable
fun CreateTable(navController: NavController) {
    // Default Values for Text Fields
    var startingChips by remember { mutableIntStateOf(1000) }
    var bigBlind by remember { mutableIntStateOf(10) }
    var smallBlind by remember { mutableIntStateOf(5) }

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
        Spacer(Modifier.height(20.dp))
        // Settings Input Fields
        IntInputField(
            label = "Starting Chips",
            initialValue = startingChips.toString(),
            maxLen = 6,
            isValid = startingChipsValid,
            onValueChange = { startingChips = it.toInt() },
            modifier = Modifier.width(240.dp)
        )
        Spacer(Modifier.height(15.dp))
        Row() {
            IntInputField(
                label = "Big Blind",
                initialValue = bigBlind.toString(),
                maxLen = 4,
                isValid = bigBlindValid,
                onValueChange = { bigBlind = it.toInt() },
                modifier = Modifier.width(160.dp)
            )
            Spacer(Modifier.width(20.dp))
            IntInputField(
                label = "Small Blind",
                initialValue = smallBlind.toString(),
                isValid = smallBlindValid,
                maxLen = 4,
                onValueChange = { smallBlind = it.toInt() },
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
        modifier = Modifier.fillMaxSize().padding(end = 25.dp, bottom = 25.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
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
                    painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.icon_play)),
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