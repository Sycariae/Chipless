package com.sycarias.chipless.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sycarias.chipless.ui.composables.InputField
import com.sycarias.chipless.ui.composables.presets.Heading

@Composable
fun SettingsScreen(navController: NavController) {
    /* THIS SCREEN IS CURRENTLY FOR TESTING - TODO: Remove testing code */

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(35.dp))

        Heading(text = "Settings")

        Spacer(modifier = Modifier.height(25.dp))

        InputField(
            label = "Test Label"
        )
    }
}
