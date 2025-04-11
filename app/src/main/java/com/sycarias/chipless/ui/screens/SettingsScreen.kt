package com.sycarias.chipless.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sycarias.chipless.ui.composables.DialogInputType
import com.sycarias.chipless.ui.composables.InputDialog
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

        Heading(text = "Testing") // TODO: Change to "Settings"

        Spacer(modifier = Modifier.height(25.dp))

        var showInputDialog by remember { mutableStateOf(true) }
        var inputValue by remember { mutableStateOf("") }
        if (showInputDialog){
            InputDialog(
                type = DialogInputType.STRING,
                prompt = "Add Player",
                inputFieldLabel = "Player Name",
                isValid = inputValue.isNotEmpty(),
                onDismiss = { showInputDialog = false },
                onConfirm = { showInputDialog = false },
                onValueChange = { inputValue = it }
            )
        }
    }
}
