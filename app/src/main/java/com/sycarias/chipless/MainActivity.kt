package com.sycarias.chipless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sycarias.chipless.ui.modifiers.customShadow
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
    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") { MainScreen(navController) }
        composable("CreateTableScreen") { CreateTableScreen(navController) }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
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

        // Image
        Image(
            painter = painterResource(id = R.drawable.cards_image), // Add your image in drawable
            contentDescription = "Poker Cards Icon",
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
                .customShadow(
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
            onClick = { /* TODO: Saved Tables Button click */ },
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
            onClick = { /* TODO: Settings Button click */ },
            modifier = Modifier
                .width(290.dp)
                .height(65.dp),
            shape = RoundedCornerShape(100.dp),
            colors = CButtonColors(),
            elevation = ButtonDefaults.buttonElevation(5.dp)
        ) {
            Text("Settings", color = CColor.textPrimary, style = CShadowStyle(style = CStyle.sh1))
        }

        Spacer(modifier = Modifier.height(25.dp))
    }
}

fun CreateTableScreen(navController: NavController) {

}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    ChiplessTheme {
        AppNavigation()
    }
}