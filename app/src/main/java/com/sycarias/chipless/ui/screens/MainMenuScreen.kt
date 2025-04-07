package com.sycarias.chipless.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sycarias.chipless.CreateTable
import com.sycarias.chipless.R
import com.sycarias.chipless.Settings
import com.sycarias.chipless.ui.composables.presets.LargeButton
import com.sycarias.chipless.ui.composables.presets.LargeButtonText
import com.sycarias.chipless.ui.composables.presets.LargeFocusButton
import com.sycarias.chipless.ui.composables.presets.LargeFocusButtonText
import com.sycarias.chipless.ui.composables.presets.Subheading
import com.sycarias.chipless.ui.composables.presets.Title

@Composable
fun MainMenuScreen(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(text = "CHIPLESS")

        Spacer(modifier = Modifier.height(10.dp))

        Subheading(text = "Bet Manager For Texas\nHold 'Em Poker")

        Spacer(modifier = Modifier.height(45.dp))

        Image(
            painter = painterResource(R.drawable.image_cards), // Cache Cards Image
            contentDescription = "Poker Cards Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(160.dp)
        )

        Spacer(modifier = Modifier.height(90.dp))

        // Create Table Button
        LargeFocusButton(
            onClick = { navController.navigate(CreateTable) }
        ) { LargeFocusButtonText(text = "Create Table") }

        Spacer(modifier = Modifier.height(25.dp))

        // Saved Tables Button
        LargeButton(
            onClick = {
                Toast.makeText(context, "COMING SOON!", Toast.LENGTH_SHORT).show()
                /* TODO: Saved Tables Button click */
            }
        ) { LargeButtonText(text = "Saved Tables") }

        Spacer(modifier = Modifier.height(20.dp))

        // Settings Button
        LargeButton(
            onClick = {
                navController.navigate(Settings)
            }
        ) { LargeButtonText(text = "Settings") }
    }
}