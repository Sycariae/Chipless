package com.sycarias.chipless.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sycarias.chipless.R
import com.sycarias.chipless.ui.extensions.buttonShadow
import com.sycarias.chipless.ui.theme.ChiplessButtonColors
import com.sycarias.chipless.ui.theme.ChiplessColors
import com.sycarias.chipless.ui.theme.ChiplessShadowStyle
import com.sycarias.chipless.ui.theme.ChiplessTypography

@Composable
fun MainMenu(navController: NavController) {
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
            style = ChiplessShadowStyle(
                style = ChiplessTypography.title,
                offsetX = -12f,
                offsetY = 12f,
                blurRadius = 25f
            ),
            color = ChiplessColors.textPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Subtitle
        Text(
            text = "Bet Manager For Texas\nHold 'Em Poker",
            style = ChiplessShadowStyle(
                style = ChiplessTypography.sh2,
                offsetX = -6f,
                offsetY = 6f,
            ),
            color = ChiplessColors.textTertiary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(45.dp))

        val cardsImage = painterResource(R.drawable.image_cards) // Add Cards Image in Drawable
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
                    color = ChiplessColors.primary,
                    offsetX = 0.dp,
                    offsetY = 0.dp,
                    blurRadius = 30.dp,
                    cornerRadius = 100.dp
                ),
            shape = RoundedCornerShape(100.dp),
            colors = ChiplessButtonColors(ChiplessColors.primary)
        ) {
            Text("Create Table", color = ChiplessColors.textPrimary, style = ChiplessShadowStyle(style = ChiplessTypography.h2, alpha = 0.5f))
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
            colors = ChiplessButtonColors(),
            elevation = ButtonDefaults.buttonElevation(5.dp)
        ) {
            Text("Saved Tables", color = ChiplessColors.textPrimary, style = ChiplessShadowStyle(style = ChiplessTypography.sh1))
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
            colors = ChiplessButtonColors(),
            elevation = ButtonDefaults.buttonElevation(5.dp)
        ) {
            Text("Settings", color = ChiplessColors.textPrimary, style = ChiplessShadowStyle(style = ChiplessTypography.sh1))
        }
    }
}