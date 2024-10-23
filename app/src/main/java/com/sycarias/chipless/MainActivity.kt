package com.sycarias.chipless

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sycarias.chipless.backend.Shadowed
import com.sycarias.chipless.backend.buttonShadow
import com.sycarias.chipless.ui.theme.ChiplessTheme
import com.sycarias.chipless.ui.theme.ChiplessButtonColors as CButtonColors
import com.sycarias.chipless.ui.theme.ChiplessColors as CColor
import com.sycarias.chipless.ui.theme.ChiplessShadowStyle as CShadowStyle
import com.sycarias.chipless.ui.theme.ChiplessTypography as CStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChiplessTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "MainScreen",
        //enterTransition = { EnterTransition.None }
    ) {
        composable("MainScreen") { MainScreen(navController) }
        composable("CreateTableScreen") { CreateTableScreen(navController) }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    // === Frontend
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "CHIPLESS",
            style = CShadowStyle(
                style = CStyle.title,
                offset = Offset(-12f, 12f),
                blurRadius = 25f
            ),
            color = CColor.textPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Subtitle
        Text(
            text = "Bet Manager For Texas\nHold 'Em Poker",
            style = CShadowStyle(
                style = CStyle.sh2,
                offset = Offset(-6f, 6f)
            ),
            color = CColor.textTertiary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(45.dp))

        val cardsImage = painterResource(id = R.drawable.image_cards) // Add Cards Image in Drawable
        Image(
            painter = remember { cardsImage }, // Cache Cards Image
            contentDescription = "Poker Cards Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(160.dp)
        )

        Spacer(modifier = Modifier.height(90.dp))

        // Create Table Button
        Button(
            onClick = { navController.navigate("CreateTableScreen") },
            modifier = Modifier
                .width(320.dp)
                .height(75.dp)
                .buttonShadow(
                    color = CColor.primary,
                    offsetX = 0.dp,
                    offsetY = 0.dp,
                    blurRadius = 30.dp,
                    cornerRadius = 100.dp
                ),
            shape = RoundedCornerShape(100.dp),
            colors = CButtonColors(CColor.primary)
        ) {
            Text("Create Table", color = CColor.textPrimary, style = CShadowStyle(style = CStyle.h2, alpha = 0.5f))
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Saved Tables Button
        Button(
            onClick = {
                Toast.makeText(context, "COMING SOON!", Toast.LENGTH_SHORT).show()
                /* TODO: Saved Tables Button click */
            },
            modifier = Modifier
                .width(290.dp)
                .height(65.dp),
            shape = RoundedCornerShape(100.dp),
            colors = CButtonColors(),
            elevation = ButtonDefaults.buttonElevation(5.dp)
        ) {
            Text("Saved Tables", color = CColor.textPrimary, style = CShadowStyle(style = CStyle.sh1))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Settings Button
        Button(
            onClick = {
                Toast.makeText(context, "COMING SOON!", Toast.LENGTH_SHORT).show()
                /* TODO: Settings Button click */
            },
            modifier = Modifier
                .width(290.dp)
                .height(65.dp),
            shape = RoundedCornerShape(100.dp),
            colors = CButtonColors(),
            elevation = ButtonDefaults.buttonElevation(5.dp)
        ) {
            Text("Settings", color = CColor.textPrimary, style = CShadowStyle(style = CStyle.sh1))
        }
    }
}

@Composable
fun CreateTableScreen(navController: NavController) {
    // === Backend

    // === Frontend - Screen-Specific Composables
    val aPBImage = painterResource(id = R.drawable.image_add_player) // Add APButton Image in Drawable
    @Composable
    fun AddPlayerButton(
        buttonId:String
    ) {
        Button(
            onClick = { /* TODO: Add Player Button click logic */ },
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp)
        ) {
            Image(
                painter = remember { aPBImage }, // Cache Add Player Button Image
                contentDescription = "Add Player Button",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    // === Frontend
    val aPBVSpacing = 32.dp // Add Player Buttons Vertical Spacing
    val aPBRowHSpacing = 88.dp // Add Player Buttons Row Horizontal Spacing
    val aPBMidHSpacing = 175.dp // Add Player Buttons Middle Horizontal Spacing

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Title
        Text(
            modifier = Modifier.padding(top = 45.dp),
            text = "Create Table",
            style = CShadowStyle(
                style = CStyle.h1,
                offset = Offset(-12f, 12f),
                blurRadius = 25f
            ),
            color = CColor.textPrimary
        )

        // Settings Input Fields
        Column() {
            // TODO: Add Input Field Composable
            // inputField("starting-chips")
            Row() {
                // inputField("Small Blind")
                // inputField("Big Blind")
            }
        }

        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // TABLE IMAGE
            Shadowed(
                blurRadius = 40.dp,
                color = CColor.primary.copy(alpha = 1f)
            ) {
                val tableImage = painterResource(id = R.drawable.image_table) // Add Table Image in Drawable
                Image(
                    painter = remember { tableImage }, // Cache Table Image
                    contentDescription = "Poker Table Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.padding(60.dp)
                )
            }

            Shadowed(
                blurRadius = 20.dp,
                color = CColor.primary.copy(alpha = 0.9f)
            ) {
                Shadowed(
                    blurRadius = 5.dp,
                    color = CColor.primary.copy(alpha = 0.25f)
                ) {
                    Column (
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
                            AddPlayerButton("top-left")
                            Spacer(modifier = Modifier.width(aPBRowHSpacing))
                            AddPlayerButton("top-right")
                        }
                        Spacer(modifier = Modifier.height(aPBVSpacing))
                        Row( // PLAYER MIDDLE SECTION
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column () { // LEFT
                                AddPlayerButton("middle-left-upper")
                                Spacer(modifier = Modifier.height(aPBVSpacing))
                                AddPlayerButton("middle-left-center")
                                Spacer(modifier = Modifier.height(aPBVSpacing))
                                AddPlayerButton("middle-left-lower")
                            }
                            Spacer(modifier = Modifier.width(aPBMidHSpacing))
                            Column () { // RIGHT
                                AddPlayerButton("middle-right-upper")
                                Spacer(modifier = Modifier.height(aPBVSpacing))
                                AddPlayerButton("middle-right-center")
                                Spacer(modifier = Modifier.height(aPBVSpacing))
                                AddPlayerButton("middle-right-lower")
                            }
                        }
                        Spacer(modifier = Modifier.height(aPBVSpacing))
                        Row( // PLAYERS BOTTOM ROW
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            AddPlayerButton("bottom-left")
                            Spacer(modifier = Modifier.width(aPBRowHSpacing))
                            AddPlayerButton("bottom-right")
                        }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 25.dp, bottom = 35.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { /* TODO: Menu Button Click */ },
                modifier = Modifier
                    .width(135.dp)
                    .height(55.dp)
                    .buttonShadow(
                        color = CColor.primary,
                        offsetX = 0.dp,
                        offsetY = 0.dp,
                        blurRadius = 20.dp,
                        cornerRadius = 100.dp
                    ),
                shape = RoundedCornerShape(100.dp),
                colors = CButtonColors(CColor.primary)
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
                Text("Play", color = CColor.textPrimary, style = CShadowStyle(style = CStyle.sh2))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    ChiplessTheme {
        AppNavigation()
    }
}